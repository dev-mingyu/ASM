package com.example.s2w.domain.seed.service;

import com.example.s2w.domain.seed.dto.SeedDTO.CreateSeedRequest;
import com.example.s2w.domain.seed.dto.SeedDTO.ReadSeedResponse;
import com.example.s2w.domain.seed.model.Seed;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SeedService {

    void createSeedRequest(List<CreateSeedRequest> seedRequestList);

    ReadSeedResponse getSeedListWithPageable(String seedId, Pageable pageable);

    List<Seed> getSeedList(String seedId);



}
