package com.ldb.backend.repository;

import com.ldb.backend.model.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByUserId(Long userId);
}
