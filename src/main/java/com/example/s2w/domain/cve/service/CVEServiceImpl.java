package com.example.s2w.domain.cve.service;

import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponse;
import com.example.s2w.domain.cve.model.CVE;
import com.example.s2w.domain.cve.repository.CVERepository;
import com.example.s2w.domain.seed.model.Seed;
import com.example.s2w.domain.seed.service.SeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CVEServiceImpl implements CVEService {

    private final SeedService seedService;
    private final CVERepository cveRepository;

    @Override
    public ReadCVEResponse ReadCVEList(String seedId) {
        List<Seed> seedList = seedService.getSeedList(seedId);
        List<CVE> cveList = cveRepository.findAll();

        return null;

    }
}
