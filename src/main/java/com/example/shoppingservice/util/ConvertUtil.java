package com.example.shoppingservice.util;

import com.example.shoppingservice.dto.xml.PurchaseType;
import com.example.shoppingservice.dto.xml.UserPurchaseInfoType;
import com.example.shoppingservice.model.Purchase;
import com.example.shoppingservice.model.UserPurchaseInfo;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ConvertUtil {
    public static String convertToXml(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static UserPurchaseInfo convertToUserPurchaseInfo(UserPurchaseInfoType userPurchaseInfoType) {
        return UserPurchaseInfo.builder()
                .name(userPurchaseInfoType.getName())
                .lastname(userPurchaseInfoType.getLastname())
                .purchaseItem(new Purchase(userPurchaseInfoType.getPurchaseType().getPurchaseItem()))
                .age(userPurchaseInfoType.getAge())
                .count(userPurchaseInfoType.getCount())
                .amount(userPurchaseInfoType.getAmount())
                .purchaseDate(userPurchaseInfoType.getPurchaseDate())
                .build();
    }

    public static UserPurchaseInfoType convertToUserPurchaseInfoType(UserPurchaseInfo userPurchaseInfo) {
        return UserPurchaseInfoType.builder()
                .name(userPurchaseInfo.getName())
                .lastname(userPurchaseInfo.getLastname())
                .purchaseType(new PurchaseType(userPurchaseInfo.getPurchaseItem().getName()))
                .age(userPurchaseInfo.getAge())
                .count(userPurchaseInfo.getCount())
                .amount(userPurchaseInfo.getAmount())
                .purchaseDate(userPurchaseInfo.getPurchaseDate())
                .build();
    }

    public static List<UserPurchaseInfoType> convertToListUserPurchaseInfoType(List<UserPurchaseInfo> list) {
        return list.stream().map(ConvertUtil::convertToUserPurchaseInfoType).collect(Collectors.toList());
    }
}
