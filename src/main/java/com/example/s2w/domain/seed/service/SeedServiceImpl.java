package com.example.s2w.domain.seed.service;

import com.example.s2w.domain.common.pageinfo.service.PageInfoService;
import com.example.s2w.domain.global.exception.type.NotFoundException;
import com.example.s2w.domain.global.response.ErrorCode;
import com.example.s2w.domain.seed.dto.SeedDTO.CreateSeedRequest;
import com.example.s2w.domain.seed.dto.SeedDTO.ReadSeedDTO;
import com.example.s2w.domain.seed.dto.SeedDTO.ReadSeedResponse;
import com.example.s2w.domain.seed.model.Seed;
import com.example.s2w.domain.seed.repository.SeedRepository;
import com.example.s2w.domain.seed.repository.SeedSoftwareRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;
    private final SeedSoftwareRepository seedSoftwareRepository;
    private final SeedSoftwareService seedSoftwareService;
    private final PageInfoService pageInfoService;

    private Seed savaSeed(CreateSeedRequest seed) {
        Seed saveSeed = Seed.builder()
                            .subDomain(seed.getSubDomain())
                            .status(seed.getStatus())
                            .ip(seed.getIp())
                            .seedId(seed.getSeedId())
                            .build();
        seedRepository.save(saveSeed);

        if(seed.getSoftwares() != null && seed.getSoftwares().size() != 0) {
            seedSoftwareService.saveSeedSoftware(saveSeed, seed.getSoftwares());
        }
        return saveSeed;
    }

    private List<ReadSeedDTO> getReadSeedDTOS(List<Seed> seedList) {
        return seedList.stream()
                       .map(content -> ReadSeedDTO.builder()
                                                  .subDomain(content.getSubDomain())
                                                  .ip(content.getIp())
                                                  .services(seedSoftwareService.getSoftwareList(content))
                                                  .build())
                       .collect(Collectors.toList());
    }


    @Override
    public void createSeedRequest(List<CreateSeedRequest> seedRequestList) {

        seedRequestList.stream()
                       .map(seed -> savaSeed(seed))
                       .collect(Collectors.toList());
    }

    @Override
    public ReadSeedResponse getSeedListWithPageable(String seedId, Pageable pageable) {
        Page<Seed> bySeedIdWithPageable = seedRepository.findBySeedIdWithPageable(seedId, pageable);

        if (bySeedIdWithPageable.getTotalElements() == 0) {
            throw new NotFoundException(ErrorCode.NOT_FOUNT_SEED_ID);
        }

        List<ReadSeedDTO> readSeedDTOList = getReadSeedDTOS(bySeedIdWithPageable.getContent());

        ReadSeedResponse response = ReadSeedResponse.builder()
                                                    .contents(readSeedDTOList)
                                                    .build();

        return pageInfoService.setPageableData(response, bySeedIdWithPageable);
    }

    @Override
    public List<Seed> getSeedList(String seedId) {
        List<Seed> seedList = seedRepository.findWithSoftwareBySeedId(seedId);
        if(seedList.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUNT_SEED_ID);
        }
        return seedList;
    }


}
