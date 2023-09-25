package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedRepository extends JpaRepository<Seed, Long>, QSeedRepository {

    @Query(value = "SELECT s FROM Seed s WHERE s.seedId = :seedId AND s.deleteYn = 'N'")
    List<Seed> findBySeedId(String seedId);

    @Query(value = "SELECT s FROM Seed s LEFT JOIN FETCH s.softwares WHERE s.seedId = :seedId AND s.deleteYn = 'N' ")
    List<Seed> findWithSoftwareBySeedId(String seedId);

}
