package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.SeedSoftware;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedSoftwareRepository extends JpaRepository<SeedSoftware, Long>, QSeedSoftwareRepository {

}
