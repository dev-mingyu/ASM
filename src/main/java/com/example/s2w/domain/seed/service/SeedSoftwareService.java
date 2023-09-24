package com.example.s2w.domain.seed.service;

import com.example.s2w.domain.seed.dto.SeedSoftWareDTO.SeedSoftwareResponse;
import com.example.s2w.domain.seed.model.Seed;
import com.example.s2w.domain.seed.model.SeedSoftware;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SeedSoftwareService {

    List<SeedSoftware> saveSeedSoftware(Seed seed, List<String> softwareList);

    List<String> getSoftwareList(Seed seed);

    SeedSoftwareResponse getSeedSoftwareList(String seedId, Pageable pageable);

}
