package com.example.shoppingservice.dto.xml;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "purchaseType")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseType {

    @XmlElement(name = "purchaseItem")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Name must contain only letters")
    @Schema(description = "название покупки")
    private String purchaseItem;
}
