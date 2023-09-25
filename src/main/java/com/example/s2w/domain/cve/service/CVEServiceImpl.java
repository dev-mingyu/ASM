package com.example.s2w.domain.cve.service;

import com.example.s2w.domain.common.pageinfo.service.PageInfoService;
import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponse;
import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponseWithPageable;
import com.example.s2w.domain.cve.model.CVE;
import com.example.s2w.domain.cve.repository.CVERepository;
import com.example.s2w.domain.seed.model.Seed;
import com.example.s2w.domain.seed.service.SeedService;
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
public class CVEServiceImpl implements CVEService {

    private final SeedService seedService;
    private final CVERepository cveRepository;
    private final PageInfoService pageInfoService;

    @Override
    public ReadCVEResponseWithPageable ReadCVEList(String seedId, Pageable pageable) {
        List<Seed> seedList = seedService.getSeedList(seedId);
        List<String> softwareList = seedList.stream()
                                            .flatMap(s -> s.getSoftwares()
                                                           .stream()
                                                           .map(sw-> sw.getName().toLowerCase()))
                                            .distinct()
                                            .collect(Collectors.toList());

        Page<CVE> cveList = cveRepository.getCVEList(softwareList, pageable);

        ReadCVEResponseWithPageable response = ReadCVEResponseWithPageable.builder()
                                                                          .contents(cveList.getContent()
                                                                                           .stream()
                                                                                           .map(cve -> getReadCVEResponseDto(cve))
                                                                                           .collect(Collectors.toList()))
                                                                          .build();

        return pageInfoService.setPageableData(response, cveList);

    }

    private ReadCVEResponse getReadCVEResponseDto(CVE cve) {
        return ReadCVEResponse.builder()
                              .cveCode(cve.getCveCode())
                              .software(cve.getSoftware())
                              .build();
    }
}
