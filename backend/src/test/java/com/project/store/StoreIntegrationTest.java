package com.project.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StoreIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("가게 ID로 특정 가게를 조회하는 통합 테스트")
    void getStoreById() throws Exception {
        // When & Then (API 호출 및 검증)
        mockMvc.perform(get("/api/stores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.local").value("강남구"))
                .andExpect(jsonPath("$.name").value("강남 한식당"));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 가게를 조회하는 통합 테스트")
    void getStoreByNonId() throws Exception {
        // When & Then (API 호출 및 검증)
        mockMvc.perform(get("/api/stores/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("잘못된 ID로 가게를 조회하는 통합 테스트")
    void getStoreByWrongId() throws Exception {
        // When & Then (API 호출 및 검증)
        mockMvc.perform(get("/api/stores/0"))
                .andExpect(status().isBadRequest());
    }
}

