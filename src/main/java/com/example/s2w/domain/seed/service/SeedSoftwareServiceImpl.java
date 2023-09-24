package com.example.s2w.domain.seed.service;

import com.example.s2w.domain.common.pageinfo.service.PageInfoService;
import com.example.s2w.domain.global.exception.type.InvalidRequestException;
import com.example.s2w.domain.global.exception.type.NotFoundException;
import com.example.s2w.domain.global.response.ErrorCode;
import com.example.s2w.domain.seed.dto.SeedSoftWareDTO.SeedSoftwareNameList;
import com.example.s2w.domain.seed.dto.SeedSoftWareDTO.SeedSoftwareResponse;
import com.example.s2w.domain.seed.model.Seed;
import com.example.s2w.domain.seed.model.SeedSoftware;
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
public class SeedSoftwareServiceImpl implements SeedSoftwareService {

    private final SeedSoftwareRepository seedSoftwareRepository;
    private final SeedRepository seedRepository;
    private final PageInfoService pageInfoService;

    private SeedSoftwareNameList getSeedSoftwareNameList(List<SeedSoftware> seedSoftwareList) {
        return SeedSoftwareNameList.builder()
                                   .services(seedSoftwareList.stream()
                                                             .map(SeedSoftware::getName)
                                                             .collect(Collectors.toList()))
                                   .build();
    }

    @Override
    public List<SeedSoftware> saveSeedSoftware(Seed seed, List<String> softwareList) {

        List<SeedSoftware> seedSoftwareList = softwareList.stream()
                                                          .map(sw -> SeedSoftware.builder()
                                                                                 .seed(seed)
                                                                                 .name(sw)
                                                                                 .build())
                                                          .collect(Collectors.toList());

        return seedSoftwareRepository.saveAll(seedSoftwareList);

    }

    @Override
    public List<String> getSoftwareList(Seed seed) {
        return seed.getSoftwares()
                   .stream()
                   .map(SeedSoftware::getName)
                   .collect(Collectors.toList());
    }

    @Override
    public SeedSoftwareResponse getSeedSoftwareList(String seedId, Pageable pageable) {
        List<Seed> bySeedId = seedRepository.findBySeedId(seedId);
        if(bySeedId.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUNT_SEED_ID);
        }

        Page<SeedSoftware> bySeedIdWithPageable = seedSoftwareRepository.findBySeedIdWithPageable(seedId, pageable);


        if (bySeedIdWithPageable.getTotalPages() < bySeedIdWithPageable.getNumber()) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }

        SeedSoftwareResponse response = SeedSoftwareResponse.builder()
                                                            .contents(getSeedSoftwareNameList(bySeedIdWithPageable.getContent()))
                                                            .build();

        return pageInfoService.setPageableData(response, bySeedIdWithPageable);
    }


}
