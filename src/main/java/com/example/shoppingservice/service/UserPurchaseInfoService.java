package com.example.shoppingservice.service;

import com.example.shoppingservice.exception.NotFoundException;
import com.example.shoppingservice.model.UserPurchaseInfo;
import com.example.shoppingservice.repository.UserPurchaseInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.shoppingservice.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class UserPurchaseInfoService {
    private final UserPurchaseInfoRepository repository;
    private final PurchaseService purchaseService;

    public UserPurchaseInfo get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("userPurchaseInfo with " + id + " not found"));
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public UserPurchaseInfo create(UserPurchaseInfo userPurchaseInfo) {
        purchaseService.checkNew(userPurchaseInfo);
        return repository.save(userPurchaseInfo);
    }


    public void update(UserPurchaseInfo userPurchaseInfo, int id) {
        UserPurchaseInfo existingPurchase = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Purchase with id " + id + " not found"));
        existingPurchase.setName(userPurchaseInfo.getName());
        existingPurchase.setLastname(userPurchaseInfo.getLastname());
        existingPurchase.setAge(userPurchaseInfo.getAge());
        existingPurchase.setPurchaseItem(userPurchaseInfo.getPurchaseItem());
        existingPurchase.setCount(userPurchaseInfo.getCount());
        existingPurchase.setAmount(userPurchaseInfo.getAmount());
        existingPurchase.setPurchaseDate(userPurchaseInfo.getPurchaseDate());

        purchaseService.checkNew(existingPurchase);
        checkNotFoundWithId(repository.save(existingPurchase), id);
    }

    public List<UserPurchaseInfo> getAll() {
        return repository.findAll();
    }

    public List<String> findPurchaseNamesInLastWeek() {
        return repository.findPurchaseNamesInLastWeek();
    }

    public List<String> findMostPurchasedItemLastMonth() {
        return repository.findMostPurchasedItemLastMonth();
    }

    public List<String> findMostPurchasedItemsForAge18() {
        return repository.findMostPurchasedItemsForAge18();
    }

    public List<Map<String, Long>> findTopBuyerInLastSixMonths() {
        return repository.findTopBuyerInLastSixMonths();
    }

    public void checkNew(int id, UserPurchaseInfo userPurchase) {
        UserPurchaseInfo existingPurchase = get(id);
        if (!existingPurchase.equals(userPurchase)) {
            userPurchase.setId(id);
            update(userPurchase, id);
        }
    }
}
