package com.example.shoppingservice.service;

import com.example.shoppingservice.model.Purchase;
import com.example.shoppingservice.model.UserPurchaseInfo;
import com.example.shoppingservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public Purchase create(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase findByName(String name) {
        return purchaseRepository.findByName(name).orElse(null);
    }

    public void checkNew(UserPurchaseInfo userPurchaseInfo) {
        String purchaseName = userPurchaseInfo.getPurchaseItem().getName();
        Purchase purchase = findByName(purchaseName);

        if (purchase != null && purchase.getName() != null && !purchase.getName().isEmpty()) {
            userPurchaseInfo.setPurchaseItem(purchase);
        } else {
            purchase = create(userPurchaseInfo.getPurchaseItem());
            userPurchaseInfo.setPurchaseItem(purchase);
        }
    }
}
