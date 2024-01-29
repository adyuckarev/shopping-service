package com.example.shoppingservice.repository;

import com.example.shoppingservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Optional<Purchase> findByName(String name);
}
