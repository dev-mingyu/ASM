package com.example.s2w.domain.seed.repository;

import com.example.s2w.domain.seed.model.Seed;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeedRepository extends JpaRepository<Seed, Long>, QSeedRepository {

    @Query(value = "SELECT s FROM Seed s WHERE s.seedId = :seedId AND s.deleteYn = 'N'")
    List<Seed> findBySeedId(String seedId);

}
