package com.example.shoppingservice.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseInfoDTO {

    private int id;

    @NotBlank
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank
    @Size(min = 2, message = "Lastname must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Lastname must contain only letters")
    private String lastname;

    @Min(1)
    @Max(100)
    private int age;

    private PurchaseDTO purchaseItem;

    @Min(1)
    @Max(100)
    private int count;

    private double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @PastOrPresent(message = "Purchase date must be present or in the future")
    private Date purchaseDate;
}
