package com.example.s2w.domain.seed.controller;

import com.example.s2w.domain.global.response.Result;
import com.example.s2w.domain.seed.dto.SeedDTO.CreateSeedRequest;
import com.example.s2w.domain.seed.dto.SeedDTO.ReadSeedResponse;
import com.example.s2w.domain.seed.dto.SeedSoftWareDTO.SeedSoftwareResponse;
import com.example.s2w.domain.seed.service.SeedService;
import com.example.s2w.domain.seed.service.SeedSoftwareService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/seeds")
@Slf4j
@RequiredArgsConstructor
@Validated
public class SeedController {

    private final SeedService seedService;
    private final SeedSoftwareService seedSoftwareService;

    @PostMapping()
    public ResponseEntity<Result> createSeed(@Valid @RequestBody @NotEmpty List<CreateSeedRequest> request) {
        seedService.createSeedRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Result.successResult(null));
    }

    @GetMapping("/{seedId}")
    public ResponseEntity<Result> getSubDomainList(@PathVariable("seedId") String seedId, Pageable pageable) {
        ReadSeedResponse seedList = seedService.getSeedListWithPageable(seedId, pageable);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Result.successResult(seedList));
    }


    @GetMapping("/{seedId}/softwares")
    public ResponseEntity<Result> getSoftWareList(@PathVariable("seedId") String seedId, Pageable pageable) {
        SeedSoftwareResponse seedSoftwareList = seedSoftwareService.getSeedSoftwareList(seedId, pageable);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Result.successResult(seedSoftwareList));
    }
}
