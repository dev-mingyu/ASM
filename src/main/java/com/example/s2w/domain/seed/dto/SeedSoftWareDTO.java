package com.example.s2w.domain.seed.dto;

import com.example.s2w.domain.common.pageinfo.dto.PageDTO.PageResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class SeedSoftWareDTO {

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeedSoftwareNameList {
        private List<String> services;
    }

    @SuperBuilder
    @Data
    @EqualsAndHashCode(callSuper=false)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeedSoftwareResponse extends PageResponse {
        private SeedSoftwareNameList contents;
    }
}
