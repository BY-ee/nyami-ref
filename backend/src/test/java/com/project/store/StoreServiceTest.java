package com.project.store;

import com.project.store.dto.StoreWithMenuResponse;
import com.project.store.dto.StoreResponse;
import com.project.store.dto.StoreSearchRequest;
import com.project.store.entity.FoodCategory;
import com.project.store.entity.Local;
import com.project.store.entity.Store;
import com.project.store.entity.Theme;
import com.project.store.repository.StoreRepository;
import com.project.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class StoreServiceTest {
    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    private List<Store> mockStores;

    private Store mockStore;

    @BeforeEach
    void setUp() {
        Local gangnam = new Local(1, "강남구");
        Local seocho = new Local(2, "서초구");
        FoodCategory korean = new FoodCategory(1, "한식");
        FoodCategory chinese = new FoodCategory(2, "중식");
        Theme soloTheme = new Theme(1, "혼밥하기 좋은");
        Theme dateTheme = new Theme(2, "데이트 코스");

        mockStores = Arrays.asList(
            new Store(1, gangnam, korean, dateTheme, "Store A", "Address A", "Detail Address A", "010-1234-5678", "image1.png", new BigDecimal("100.000"), new BigDecimal("200.000"), "Description A", 100),
            new Store(2, gangnam, chinese, soloTheme, "Store B", "Address B", "Detail Address B", "010-8765-4321", "image2.png", new BigDecimal("300.000"), new BigDecimal("400.000"), "Description B", 300),
            new Store(3, seocho, chinese, dateTheme, "Store C", "Address C", "Detail Address C", "010-5678-1234", "image3.png", new BigDecimal("500.000"), new BigDecimal("600.000"), "Description C", 400),
            new Store(4, seocho, korean, soloTheme, "Store D", "Address D", "Detail Address D", "010-4321-8765", "image4.png", new BigDecimal("700.000"), new BigDecimal("800.000"), "Description D", 200)
        );
        mockStore = new Store(1, gangnam, korean, dateTheme, "Store A", "Address A", "Detail Address A", "010-1234-5678", "image1.png", new BigDecimal("100.000"), new BigDecimal("200.000"), "Description A", 100);
    }

    // 필터 조건 없음
    @Test
    @DisplayName("모든 가게 목록을 조회합니다.")
    void getStores() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        List<Store> filteredStores = mockStores.stream()
                .toList();

        StoreSearchRequest request = new StoreSearchRequest();
        request.setLocal(null);
        request.setFoodCategory(null);
        request.setTheme(null);
        request.setSort(null);
        request.setPage(1);

        when(storeRepository.findStores(
                argThat(local -> local == null),
                argThat(category -> category == null),
                argThat(theme -> theme == null),
                argThat(sort -> sort == null),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(filteredStores, pageable, filteredStores.size()));

        // When
        Page<StoreResponse> stores = storeService.findStores(request);

        // Then
        assertNotNull(stores.getContent());
        assertEquals(4, stores.getNumberOfElements());
    }

    // 지역 필터 적용
    @Test
    @DisplayName("지역이 \"강남구\"인 가게 목록을 조회합니다.")
    void getStoresByLocal() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        List<Store> filteredStores = mockStores.stream()
                .filter(store -> store.getLocal().getLocal().equals("강남구"))
                .toList();

        StoreSearchRequest request = new StoreSearchRequest();
        request.setLocal("강남구");
        request.setFoodCategory(null);
        request.setTheme(null);
        request.setSort(null);
        request.setPage(1);

        when(storeRepository.findStores(
                argThat(local -> local.equals("강남구")),
                argThat(category -> category == null),
                argThat(theme -> theme == null),
                argThat(sort -> sort == null),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(filteredStores, pageable, filteredStores.size()));

        // When
        Page<StoreResponse> stores = storeService.findStores(request);

        // Then
        assertNotNull(stores.getContent());
        assertEquals(2, stores.getNumberOfElements());
        assertEquals("강남구", stores.getContent().get(0).getLocal());
        assertEquals("강남구", stores.getContent().get(1).getLocal());
    }

    // 지역 + 테마 필터 적용
    @Test
    @DisplayName("지역이 \"강남구\"이고 테마가 \"혼밥하기 좋은\"인 가게 목록을 조회합니다.")
    void getStoresByLocalAndTheme() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        List<Store> filteredStores = mockStores.stream()
                .filter(store -> store.getLocal().getLocal().equals("강남구") && store.getTheme().getTheme().equals("혼밥하기 좋은"))
                .toList();

        StoreSearchRequest request = new StoreSearchRequest();
        request.setLocal("강남구");
        request.setFoodCategory(null);
        request.setTheme("혼밥하기 좋은");
        request.setSort(null);
        request.setPage(1);

        when(storeRepository.findStores(
                argThat(local -> local.equals("강남구")),
                argThat(category -> category == null),
                argThat(theme -> theme.equals("혼밥하기 좋은")),
                argThat(sort -> sort == null),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(filteredStores, pageable, filteredStores.size()));

        // When
        Page<StoreResponse> stores = storeService.findStores(request);

        // Then
        assertNotNull(stores.getContent());
        assertEquals(1, stores.getNumberOfElements());
        assertEquals("강남구", stores.getContent().get(0).getLocal());
        assertEquals("혼밥하기 좋은", stores.getContent().get(0).getTheme());
    }

    // 업종 필터 적용
    @Test
    @DisplayName("업종이 \"한식\"인 가게 목록을 조회합니다.")
    void getStoresByFoodCategory() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        List<Store> filteredStores = mockStores.stream()
                .filter(store -> store.getFoodCategory().getFoodCategory().equals("한식"))
                .toList();

        StoreSearchRequest request = new StoreSearchRequest();
        request.setLocal(null);
        request.setFoodCategory("한식");
        request.setTheme(null);
        request.setSort(null);
        request.setPage(1);

        when(storeRepository.findStores(
                argThat(local -> local == null),
                argThat(category -> category.equals("한식")),
                argThat(theme -> theme == null),
                argThat(sort -> sort == null),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(filteredStores, pageable, filteredStores.size()));

        // When
        Page<StoreResponse> stores = storeService.findStores(request);

        // Then
        assertNotNull(stores.getContent());
        assertEquals(2, stores.getNumberOfElements());
        assertEquals("한식", stores.getContent().get(0).getFoodCategory());
        assertEquals("한식", stores.getContent().get(1).getFoodCategory());
    }

    // 필터 조건 미적용 (잘못된 데이터)
    @Test
    @DisplayName("잘못된 데이터를 전달한 경우의 가게 목록을 조회합니다.")
    void getStoresWithWrongData() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        List<Store> filteredStores = mockStores.stream()
                .filter(store -> store.getLocal().getLocal().equals("test") &&
                                 store.getFoodCategory().getFoodCategory().equals("test") &&
                                 store.getTheme().getTheme().equals("test"))
                .toList();

        StoreSearchRequest request = new StoreSearchRequest();
        request.setLocal("test");
        request.setFoodCategory("test");
        request.setTheme("test");
        request.setSort("test");
        request.setPage(1);

        when(storeRepository.findStores(
                argThat(local -> local.equals("test")),
                argThat(category -> category.equals("test")),
                argThat(theme -> theme.equals("test")),
                argThat(sort -> sort.equals("test")),
                any(Pageable.class)
        )).thenReturn(new PageImpl<>(filteredStores, pageable, filteredStores.size()));

        // When
        Page<StoreResponse> stores = storeService.findStores(request);

        // Then
        assertNotNull(stores.getContent());
        assertTrue(stores.isEmpty());
    }

    // 특정 가게 조회
    @Test
    @DisplayName("가게의 id로 특정 가게를 조회합니다.")
    void getStoreById() {
        // Given
        StoreWithMenuResponse storeDto = new StoreWithMenuResponse();
        storeDto.setId(1);
        storeDto.setLocal("강남구");
        storeDto.setFoodCategory("한식");
        storeDto.setTheme("혼밥하기 좋은");
        storeDto.setName("테스트 가게");
        when(storeRepository.findStoreById(anyInt())).thenReturn(Optional.of(storeDto));

        // When
        Optional<StoreWithMenuResponse> result = storeService.findStoreById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("강남구", result.get().getLocal());
    }

    // 잘못된 데이터의 가게 조회
    @Test
    @DisplayName("잘못된 id를 전달한 경우의 가게를 조회합니다.")
    void getStoreByWrongId() {
        // Given
        when(storeRepository.findStoreById(anyInt())).thenReturn(Optional.empty());

        // When
        Optional<StoreWithMenuResponse> result = storeService.findStoreById(0);

        // Then
        assertTrue(result.isEmpty());
    }
}
