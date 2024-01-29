package com.example.shoppingservice.repository;

import com.example.shoppingservice.model.UserPurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public interface UserPurchaseInfoRepository extends JpaRepository<UserPurchaseInfo, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM UserPurchaseInfo pi WHERE pi.id=:id")
    int delete(@Param("id") int id);

    @Query(value = "SELECT p.name " + "" +
            "FROM user_purchase_info ui " +
            "JOIN purchase p ON ui.purchase_id = p.id " +
            "WHERE ui.purchase_date BETWEEN CURRENT_DATE - INTERVAL '1 week' AND CURRENT_DATE", nativeQuery = true)
    List<String> findPurchaseNamesInLastWeek();

    @Query(value = "SELECT p.name " +
            "FROM user_purchase_info ui " +
            "JOIN purchase p ON ui.purchase_id = p.id " +
            "WHERE ui.purchase_date BETWEEN CURRENT_DATE - INTERVAL '1 month' AND CURRENT_DATE " +
            "GROUP BY p.name " +
            "ORDER BY SUM(ui.count) DESC " +
            "LIMIT 1", nativeQuery = true)
    List<String> findMostPurchasedItemLastMonth();

    @Query("SELECT pi.purchaseItem.name " +
            "FROM UserPurchaseInfo pi " +
            "WHERE pi.age = 18 " +
            "GROUP BY pi.purchaseItem.name " +
            "ORDER BY COUNT(pi.id) DESC")
    List<String> findMostPurchasedItemsForAge18();

    @Query(value = "SELECT CONCAT(ui.name, ' ', ui.lastname) AS name, COUNT(ui.id) AS purchaseCount " +
            "FROM user_purchase_info ui " +
            "WHERE ui.purchase_date BETWEEN CURRENT_DATE - INTERVAL '6 months' AND CURRENT_DATE " +
            "GROUP BY name, lastname, age " +
            "ORDER BY purchaseCount DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Map<String, Long>> findTopBuyerInLastSixMonths();
}
