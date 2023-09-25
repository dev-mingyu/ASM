package com.example.s2w.domain.cve.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.s2w.domain.cve.service.CVEService;
import com.example.s2w.domain.global.enumeration.SeedStatus;
import com.example.s2w.domain.seed.dto.SeedDTO.CreateSeedRequest;
import com.example.s2w.domain.seed.repository.SeedRepository;
import com.example.s2w.domain.seed.repository.SeedSoftwareRepository;
import com.example.s2w.domain.seed.service.SeedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
class CVEControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SeedService seedService;

    @Autowired
    private EntityManager entityManager;

    MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application",
        "json",
        java.nio.charset.Charset.forName("UTF-8"));

    private List<CreateSeedRequest> seedRequestList = new ArrayList<>();
    private String seedId = "SEED_8664d1e419";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation)
        throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                      .addFilters(new CharacterEncodingFilter("UTF-8",
                                          true))
                                      .apply(documentationConfiguration(restDocumentation))
                                      .alwaysDo(document("{method-name}",
                                          preprocessRequest(prettyPrint()),
                                          preprocessResponse(prettyPrint())))
                                      .build();

        seedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e419")
                                             .subDomain("cafe.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("3.38.222.16")
                                             .softwares(Arrays.asList("Nginx", "Tomcat"))
                                             .build());

        seedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e419")
                                             .subDomain("nid.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("52.78.116.214")
                                             .softwares(Arrays.asList("React","Java"))
                                             .build());

        seedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e419")
                                             .subDomain("shopping.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("43.201.3.235")
                                             .softwares(Arrays.asList("PWA","styled-components","Vue.js"))
                                             .build());
    }

    @Test
    @DisplayName("CVE 목록 조회 API")
    void readCveListBySeedId() throws Exception {
        seedService.createSeedRequest(seedRequestList);
        entityManager.flush();
        entityManager.clear();

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/cve/seeds/{seedId}?page={page}&size={size}", seedId, 1, 10)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andDo(document("read-cve-list-by-seed-id",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                parameterWithName("seedId").description("Seed Id")
                            ),
                            requestParameters(
                                parameterWithName("page").description("요청 페이지").optional(),
                                parameterWithName("size").description("페이지 당 보여질 요소 개수").optional()
                            ),
                            PayloadDocumentation.responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("요소의 총 개수"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 개수"),
                                fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지의 요소 크기"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("data.contents[].cveCode").type(JsonFieldType.STRING).description("서브 도메인"),
                                fieldWithPath("data.contents[].software").type(JsonFieldType.STRING).description("Ip Address")
                            )
                        )
                    )
                    .andDo(print())
                    .andReturn();
    }

    @Test
    @DisplayName("CVE 목록 조회 API - 페이지 범위 초과")
    void readCveListBySeedIdWithOverflowPage() throws Exception {
        seedService.createSeedRequest(seedRequestList);
        entityManager.flush();
        entityManager.clear();

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/cve/seeds/{seedId}?page={page}&size={size}", seedId, 2, 10)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn();
    }

    @Test
    @DisplayName("CVE 목록 조회 API - SeedId 없음")
    void readCveListBySeedIdWithoutSeedId() throws Exception {

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/cve/seeds/{seedId}?page={page}&size={size}", "SEED_8664d1e410", 1, 2)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn();
    }
}