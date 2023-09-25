package com.example.s2w.domain.seed.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.s2w.domain.global.enumeration.SeedStatus;
import com.example.s2w.domain.global.response.Code;
import com.example.s2w.domain.global.response.Result;
import com.example.s2w.domain.seed.dto.SeedDTO.CreateSeedRequest;
import com.example.s2w.domain.seed.service.SeedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
class SeedControllerTest {

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
    @DisplayName("Seed 스캔 결과 저장")
    void createSeed() throws Exception {
        String content = objectMapper.writeValueAsString(seedRequestList);
        entityManager.flush();
        entityManager.clear();

        this.mockMvc.perform(
                    post("/api/v1/seeds")
                                                .contextPath("/api")
                                                .content(content)
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isCreated())
                    .andDo(document("create-seed",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                fieldWithPath("[].subDomain").type(JsonFieldType.STRING).description("서브 도메인"),
                                fieldWithPath("[].status").type(JsonFieldType.STRING).description("활성 상태"),
                                fieldWithPath("[].ip").type(JsonFieldType.STRING).description("Ip Address"),
                                fieldWithPath("[].softwares[]").type(JsonFieldType.ARRAY).description("실행중인 소프트웨어 목록"),
                                fieldWithPath("[].seedId").type(JsonFieldType.STRING).description("Seed Id")
                                ),
                            PayloadDocumentation.responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                            )
                        )
                    )
                    .andDo(print())
                    .andReturn();
    }

    @Test
    @DisplayName("Seed 스캔 결과 저장 - 유효하지 않은 요청")
    void createSeed_InvalidRequest() throws Exception {
        List<CreateSeedRequest> invalidSeedRequestList = new ArrayList<>();
        invalidSeedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e419#")
                                             .subDomain("cafe.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("3.38.222.16")
                                             .softwares(Arrays.asList("Nginx", "Tomcat"))
                                             .build());

        invalidSeedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e419")
                                             .subDomain("nid.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("52.78.116.514")
                                             .softwares(Arrays.asList("React","Java"))
                                             .build());

        invalidSeedRequestList.add(CreateSeedRequest.builder()
                                             .seedId("SEED_8664d1e4191")
                                             .subDomain("shopping.naver.com")
                                             .status(SeedStatus.active)
                                             .ip("43.201.3.535")
                                             .softwares(Arrays.asList("PWA","styled-components","Vue.js"))
                                             .build());

        String content = objectMapper.writeValueAsString(invalidSeedRequestList);

        // 2. 해당 요청으로 API 호출
        MvcResult mvcResult = this.mockMvc.perform(
                                      post("/api/v1/seeds")
                                          .contextPath("/api")
                                          .content(content)
                                          .accept(MEDIA_TYPE_JSON_UTF8)
                                          .contentType(MEDIA_TYPE_JSON_UTF8))
                                          .andExpect(status().isOk())
                                          .andDo(print())
                                          .andReturn();


        String responseContent = mvcResult.getResponse().getContentAsString();
        Result result = objectMapper.readValue(responseContent, Result.class);

        assertEquals(Code.ERROR, result.getCode());
        assertTrue(result.getMessage().contains("createSeed.request[0].seedId"));
        assertTrue(result.getMessage().contains("createSeed.request[1].ip"));
        assertTrue(result.getMessage().contains("createSeed.request[2].seedId"));
        assertTrue(result.getMessage().contains("createSeed.request[2].ip"));
    }

    @Test
    @DisplayName("서브 도메인 목록 조회")
    void getSubDomainList() throws Exception{
        seedService.createSeedRequest(seedRequestList);
        entityManager.flush();
        entityManager.clear();

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/seeds/{seedId}?page={page}&size={size}", seedId, 1, 2)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isCreated())
                    .andDo(document("get-sub-domain-list",
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
                                fieldWithPath("data.contents[].subDomain").type(JsonFieldType.STRING).description("서브 도메인"),
                                fieldWithPath("data.contents[].ip").type(JsonFieldType.STRING).description("Ip Address"),
                                fieldWithPath("data.contents[].services[]").type(JsonFieldType.ARRAY).description("실행중인 소프트웨어 목록").optional()
                            )
                        )
                    )
                    .andDo(print())
                    .andReturn();

    }

    @Test
    @DisplayName("서브 도메인 목록 조회 with Invalid Page")
    void getSubDomainListWithInvalidPage() throws Exception {
        seedService.createSeedRequest(seedRequestList);
        entityManager.flush();
        entityManager.clear();

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/seeds/{seedId}?page={page}&size={size}", seedId, 3, 2)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn();
    }

    @Test
    @DisplayName("서브 도메인 목록 조회 with Non-Existent SeedId")
    void getSubDomainListWithNonExistentSeedId() throws Exception {

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/seeds/{seedId}?page={page}&size={size}", seedId, 1, 2)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andReturn();
    }

    @Test
    @DisplayName("소프트웨어 목록 조회")
    void getSoftWareList() throws Exception {
        seedService.createSeedRequest(seedRequestList);

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/seeds/{seedId}/softwares?page={page}&size={size}", seedId, 1, 2)
                                                .contextPath("/api")
                                                .accept(MEDIA_TYPE_JSON_UTF8)
                                                .contentType(MEDIA_TYPE_JSON_UTF8))
                    .andExpect(status().isCreated())
                    .andDo(document("get-software-list",
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
                                fieldWithPath("data.contents.services[]").type(JsonFieldType.ARRAY).description("실행중인 소프트웨어 목록").optional()
                            )
                        )
                    )
                    .andDo(print())
                    .andReturn();

    }
}