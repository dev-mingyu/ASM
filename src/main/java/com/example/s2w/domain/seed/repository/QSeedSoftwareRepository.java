package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.SeedSoftware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QSeedSoftwareRepository {

    Page<SeedSoftware> findBySeedIdWithPageable(String seedId, Pageable pageable);

}
