package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.Seed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QSeedRepository {

    Page<Seed> findBySeedIdWithPageable(String seedId, Pageable pageable);
}
