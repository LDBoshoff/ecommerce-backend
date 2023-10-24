package com.ldb.backend.service;

import com.ldb.backend.model.Store;
import com.ldb.backend.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getStoresByUserId(Long userId) {
        return storeRepository.findAllStoresByUserId(userId);
    }

    public Store createStore(Store newStore) {
        return storeRepository.save(newStore);
    }

    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }

    public void deleteStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);

        if (store != null) {
            storeRepository.deleteById(storeId);
        } 
    }

}