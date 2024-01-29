package com.example.shoppingservice.mapper;

import com.example.shoppingservice.dto.UserPurchaseInfoDTO;
import com.example.shoppingservice.model.UserPurchaseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserPurchaseInfoMapper {

    @Mappings({
            @Mapping(target = "purchaseItem", source = "purchaseItem")
    })
    UserPurchaseInfoDTO toDto(UserPurchaseInfo entity);

    @Mappings({
            @Mapping(target = "purchaseItem", source = "purchaseItem")
    })
    UserPurchaseInfo toEntity(UserPurchaseInfoDTO dto);
}
