package com.be.spring.controller;


import com.be.spring.config.jwt.JwtFactory;
import com.be.spring.management.config.jwt.JwtProperties;
import com.be.spring.management.config.jwt.TokenProvider;
import com.be.spring.management.dto.CreateAccessTokenRequest;
import com.be.spring.management.dto.JwtToken;
import com.be.spring.management.entity.RefreshToken;
import com.be.spring.management.entity.User;
import com.be.spring.management.repository.RefreshTokenRepository;
import com.be.spring.management.repository.UserRepository;
import com.be.spring.management.service.RefreshTokenService;
import com.be.spring.management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }




    @DisplayName("createNewAccessToken : 새로운 액세스 토큰을 발급한다.")
    @Test
    public void
    createNewAccessToken() throws Exception {

        // 테스트 유저 생성 및 로그인
        String email = "test@test.com";
        String password = "test_password";
        JwtToken token = userService.login(email, password);

        // 테스트 유저의 리프레시 토큰 저장
        refreshTokenService.saveRefreshToken(userService.getUserIdByEmail(email), token.getRefreshToken());

        // 액세스 토큰 만료 후, 리프레시 토큰을 가지고 새로운 액세스 토큰 요청
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/refresh")
                        .header("Authorization", "Bearer " + token.getRefreshToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 새로 발급받은 액세스 토큰 확인
        String newAccessToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.accessToken");
        assertNotNull(newAccessToken);
        assertTrue(tokenProvider.validToken(newAccessToken));
    }
//        // given
//        // 테스트 유저 생성, 리프레시 토큰을 만들어 DB에 저장, 토큰 생성 API 본문에 리프레시 토큰을 포함하여 요청 객체 생성
//        final String url = "/api/token";
//
//        User testUser = userRepository.save(User.builder()
//                .email("user@gmail.com")
//                .password("test")
//                .userId("test")
//                .build());
//
//        String refreshToken = JwtFactory.builder()
//                .claims(Map.of("id", testUser.getId()))
//                .build()
//                .createToken(jwtProperties);
//
//        refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToken));
//
//        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
//        request.setRefreshToken(refreshToken);
//
//        final String requestBody = objectMapper.writeValueAsString(request);
//
//        // when
//        // 토큰 추가 API에 요청을 보냄. Type은 JSON
//        ResultActions resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        // then
//        // 응답 코드를 확인하고 응답으로 온 액세스 토큰이 비어 있지 않은지 확인
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accessToken").isNotEmpty());
//    }
}
