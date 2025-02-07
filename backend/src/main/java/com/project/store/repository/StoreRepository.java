package com.project.store.repository;

import com.project.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>, StoreRepositoryCustom {
    @Modifying
    @Query("UPDATE Store s SET s.views = s.views + 1 WHERE s.id = :storeId")
    void increaseViewCount(@Param("storeId") int storeId);
}
