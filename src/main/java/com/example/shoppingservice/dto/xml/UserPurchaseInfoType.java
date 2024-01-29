package com.example.shoppingservice.dto.xml;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "userPurchaseInfoType")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPurchaseInfoType {

    @XmlElement(name = "name")
    @NotBlank
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Name must contain only letters")
    @Schema(description = "Имя (например, Иван)")
    private String name;

    @XmlElement(name = "lastname")
    @NotBlank
    @Size(min = 2, message = "Lastname must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Lastname must contain only letters")
    @Schema(description = "Фамилия (Например, Иванов)")
    private String lastname;

    @XmlElement(name = "age")
    @Min(1)
    @Max(100)
    @Schema(description = "Возраст (например, 10)")
    private int age;

    @XmlElement(name = "purchaseType")
    @Schema(description = "Содержимое покупки (ссылка на Покупку)")
    private PurchaseType purchaseType;

    @XmlElement(name = "count")
    @Min(1)
    @Max(100)
    @Schema(description = "Количество товара (например, 3)")
    private int count;

    @XmlElement(name = "amount")
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Schema(description = "Сумма покупки (например, 147.50)")
    private double amount;

    @XmlElement(name = "purchaseDate")
    @NotNull
    @PastOrPresent(message = "Purchase date must be present or in the past")
    @Schema(description = "Дата покупки (например, 2023-12-05)")
    private Date purchaseDate;
}
