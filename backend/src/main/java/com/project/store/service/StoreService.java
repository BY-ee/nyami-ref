package com.project.store.service;

import com.project.store.dto.StoreWithMenuResponse;
import com.project.store.dto.StoreResponse;
import com.project.store.dto.StoreSearchRequest;
import com.project.store.entity.Store;
import com.project.store.mapper.StoreMapper;
import com.project.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    int size = 6;

    public Page<StoreResponse> findStores(StoreSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), size, Sort.by("views").descending());
        Page<Store> stores = storeRepository.findStores(
                request.getLocal(),
                request.getFoodCategory(),
                request.getTheme(),
                request.getSort(),
                pageable
        );
        List<StoreResponse> responses = StoreMapper.STORE_MAPPER.toDto(stores.getContent());

        return new PageImpl<>(responses, pageable, stores.getTotalElements());
    }

    public Optional<StoreWithMenuResponse> findStoreById(int storeId) {
        return storeRepository.findStoreById(storeId);
    }

    @Transactional
    public void increaseViewCount(int storeId) {
        storeRepository.increaseViewCount(storeId);
    }
}
