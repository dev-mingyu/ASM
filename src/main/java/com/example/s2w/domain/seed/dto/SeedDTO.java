package com.example.s2w.domain.seed.dto;

import static com.example.s2w.domain.common.utils.Pattern.IPV4_PATTERN;
import static com.example.s2w.domain.common.utils.Pattern.SEED_ID_PATTERN;

import com.example.s2w.domain.common.pageinfo.dto.PageDTO.PageResponse;
import com.example.s2w.domain.global.enumeration.SeedStatus;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class SeedDTO {
    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateSeedRequest {

        @NotNull(message = "Subdomain cannot be null")
        private String subDomain;
        @NotNull(message = "Status cannot be null")
        private SeedStatus status;
        @NotNull(message = "IP address cannot be null")
        @Pattern(regexp = IPV4_PATTERN, message="IP address format is incorrect")
        private String ip;
        private List<String> softwares;
        @NotNull(message = "Seed ID cannot be null")
        @Pattern(regexp = SEED_ID_PATTERN, message = "Invalid seedId format")
        private String seedId;
    }

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadSeedDTO {
        private String subDomain;
        private String ip;
        private List<String> services;
    }

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadSeedResponse extends PageResponse {
        private List<ReadSeedDTO> contents;
    }

}
