package com.example.s2w.domain.seed.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.s2w.domain.global.exception.type.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SeedTest {
    private Seed seed;

    @Test
    @DisplayName("SeedId 앞 5자리 규격 검증")
    void validSeedIdWithInvalidLength() throws Exception {
        //given
        String seedId = "aEED_qwert12345";

        //when
        Seed seed = Seed.builder()
                        .seedId(seedId)
                        .build();

        //then
        assertThrows(InvalidRequestException.class, seed::validSeedId);
    }

    @Test
    @DisplayName("SeedId 15자리 검증")
    void validSeedIdWithInvalidLength2() throws Exception {
        //given
        String seedId = "SEED_qwert1234";

        //when
        Seed seed = Seed.builder()
                        .seedId(seedId)
                        .build();

        //then
        assertThrows(InvalidRequestException.class, seed::validSeedId);
    }

    @Test
    @DisplayName("SeedId 뒤 10자리 숫자,문자 조합 10자리 검증")
    void validSeedIdWithInvalidPattern() throws Exception {
        //given
        String seedId = "SEED_qwert1234*";

        //when
        Seed seed = Seed.builder()
                                   .seedId(seedId)
                                   .build();

        //then
        assertThrows(InvalidRequestException.class, seed::validSeedId);
    }

    @Test
    @DisplayName("정상 SeedId 검증")
    void validSeedIdWithValidPattern() throws Exception {
        //given
        String seedId = "SEED_qwert12345";

        //when
        Seed seed_qwert12345 = Seed.builder()
                                   .seedId(seedId)
                                   .build();

        //then
        assertDoesNotThrow(seed_qwert12345::validSeedId);
    }


    @Test
    void validIpWithInvalidIp() {
        //given
        String ip = "255.255.255.777";

        //when
        Seed seed = Seed.builder()
                        .ip(ip)
                        .build();

        //then
        assertThrows(InvalidRequestException.class, seed::validIp);
    }

    @Test
    void validIpWithValidIp() {
        //given
        String ip = "192.168.1.1";

        //when
        Seed seed = Seed.builder()
                         .ip(ip)
                         .build();

        //then
        assertDoesNotThrow(seed::validIp);
    }
}