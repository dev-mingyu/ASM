package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedRepository extends JpaRepository<Seed, Long>, QSeedRepository {

}
