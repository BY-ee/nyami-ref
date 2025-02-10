package com.project.store;

import com.project.common.config.QuerydslConfig;
import com.project.store.dto.StoreWithMenuResponse;
import com.project.store.entity.Store;
import com.project.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
@ExtendWith(SpringExtension.class)
@Import(QuerydslConfig.class)  // QuerydslConfig 추가
@ActiveProfiles("test")
public class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("1페이지의 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresPageOne() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores(null, null, null, null, pageable);

        // Then
        assertNotNull(stores);
        assertEquals(6, stores.getNumberOfElements());
        assertEquals(300, stores.getContent().get(0).getViews());
    }

    @Test
    @DisplayName("2페이지의 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresPageTwo() {
        // Given
        Pageable pageable = PageRequest.of(1, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores(null, null, null, null, pageable);

        // Then
        assertNotNull(stores);
        assertEquals(4, stores.getNumberOfElements());
        assertEquals(100, stores.getContent().get(0).getViews());
    }

    @Test
    @DisplayName("지역이 \"강남구\"인 1페이지의 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresPageOneByLocal() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores("강남구", null, null, null, pageable);

        // Then
        assertNotNull(stores);
        assertEquals("강남구", stores.getContent().get(0).getLocal().getLocal());
        assertEquals("강남구", stores.getContent().get(1).getLocal().getLocal());
    }

    @Test
    @DisplayName("지역이 \"강남구\"이고 테마가 \"혼밥하기 좋은\"인 1페이지의 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresPageOneByLocalAndTheme() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores("강남구", null, "혼밥하기 좋은", null, pageable);

        // Then
        assertNotNull(stores);
        assertEquals("강남구", stores.getContent().get(0).getLocal().getLocal());
        assertEquals("혼밥하기 좋은", stores.getContent().get(0).getTheme().getTheme());
    }

    @Test
    @DisplayName("업종이 \"한식\"인 1페이지의 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresPageOneByFoodCategory() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores(null, "한식", null, null, pageable);

        // Then
        assertNotNull(stores);
        assertEquals("한식", stores.getContent().get(0).getFoodCategory().getFoodCategory());
    }

    @Test
    @DisplayName("잘못된 데이터를 전달한 경우에 가게 목록을 데이터베이스에서 조회합니다.")
    void findStoresWithWrongData() {
        // Given
        Pageable pageable = PageRequest.of(0, 6, Sort.by("views").descending());

        // When
        Page<Store> stores = storeRepository.findStores("test", "test", "test", "test", pageable);

        // Then
        assertNotNull(stores);
        assertEquals(0, stores.getNumberOfElements());
        assertEquals(new ArrayList<>(), stores.getContent());
    }

    @Test
    @DisplayName("가게의 id로 특정 가게를 데이터베이스에서 조회합니다.")
    void findStoreById() {
        // When
        Optional<StoreWithMenuResponse> store = storeRepository.findStoreById(5);

        // Then
        assertTrue(store.isPresent());
        assertEquals(5, store.get().getId());
        assertTrue(2 <= store.get().getMenus().size());
    }
    
    @Test
    @DisplayName("잘못된 id를 전달한 경우에 가게를 데이터베이스에서 조회합니다.")
    void findStoreByWrongId() {
        // When
        Optional<StoreWithMenuResponse> store = storeRepository.findStoreById(0);

        // Then
        assertTrue(store.isEmpty());
    }
}
