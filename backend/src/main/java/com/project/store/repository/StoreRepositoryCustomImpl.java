package com.project.store.repository;

import com.project.store.dto.MenuDto;
import com.project.store.dto.StoreWithMenuDto;
import com.project.store.entity.QMenu;
import com.project.store.entity.QStore;
import com.project.store.entity.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

// Querydsl 인터페이스의 구현체 (구현체에 동적 쿼리 작성)
@Repository
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Store> findStores(String local, String foodCategory, String theme, String sort, Pageable pageable) {
        QStore store = QStore.store;

        BooleanBuilder whereClause = new BooleanBuilder();
        if (local != null) whereClause.and(store.local.local.eq(local));
        if (foodCategory != null) whereClause.and(store.foodCategory.foodCategory.eq(foodCategory));
        if (theme != null) whereClause.and(store.theme.theme.eq(theme));

        List<Store> result = queryFactory
                .selectFrom(store)
                .leftJoin(store.local).fetchJoin()
                .leftJoin(store.foodCategory).fetchJoin()
                .leftJoin(store.theme).fetchJoin()
                .where(whereClause)
                .orderBy(pageable.getSort().isSorted() ? store.views.desc() : store.views.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())  // 총 개수 조회
                .from(store)
                .where(whereClause);

        long total = Optional.ofNullable(countQuery.fetchOne())
                .orElse(0L); // 총 개수 조회

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Optional<StoreWithMenuDto> findStoreById(int id) {
        QStore store = QStore.store;
        QMenu menu = QMenu.menu;

        List<Tuple> result = queryFactory
                .select(store.id, store.local.local, store.foodCategory.foodCategory, store.theme.theme,
                        store.name, store.address, store.detailAddress, store.tel, store.image, store.x,
                        store.y, store.description, store.views,
                        menu.id, menu.name, menu.price, menu.description, menu.image)
                .from(store)
                .leftJoin(menu).on(menu.store.id.eq(store.id))
                .where(store.id.eq(id))
                .fetch();

        // 중복된 가게를 제거하는 Map 객체
        Map<Integer, StoreWithMenuDto> storeMap = new HashMap<>();

        for (Tuple row : result) {
            int storeId = Optional.ofNullable(row.get(store.id)).orElse(0);

            // 기존 StoreWithMenuDto가 없으면 새로 생성
            StoreWithMenuDto storeDto = storeMap.computeIfAbsent(storeId, idKey -> new StoreWithMenuDto(
                    idKey,
                    row.get(store.local.local),
                    row.get(store.foodCategory.foodCategory),
                    row.get(store.theme.theme),
                    row.get(store.name),
                    row.get(store.address),
                    row.get(store.detailAddress),
                    row.get(store.tel),
                    row.get(store.image),
                    row.get(store.x),
                    row.get(store.y),
                    row.get(store.description),
                    Optional.ofNullable(row.get(store.views)).orElse(0),
                    new ArrayList<>()
            ));

            // 메뉴가 존재하면 추가
            if (row.get(menu.id) != null) {
                storeDto.getMenus().add(new MenuDto(
                        Optional.ofNullable(row.get(menu.id)).orElse(0),
                        row.get(menu.name),
                        Optional.ofNullable(row.get(menu.price)).orElse(0),
                        row.get(menu.description),
                        row.get(menu.image)
                ));
            }
        }

        return Optional.ofNullable(storeMap.get(id));
    }

}
