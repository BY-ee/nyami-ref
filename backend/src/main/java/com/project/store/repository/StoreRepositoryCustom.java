package com.project.store.repository;

import com.project.store.dto.StoreWithMenuResponse;
import com.project.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// 동적 쿼리 작성을 위한 Querydsl 인터페이스
public interface StoreRepositoryCustom {
    Page<Store> findStores(String local, String foodCategory, String theme, String sort, Pageable pageable);
    Optional<StoreWithMenuResponse> findStoreById(int id);
}
