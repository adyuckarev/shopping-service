package com.example.shoppingservice.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "user_purchase_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseInfo extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchaseItem;

    @Min(1)
    @Max(100)
    private int count;

    private double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @PastOrPresent(message = "Purchase date must be present or in the past")
    private Date purchaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserPurchaseInfo that = (UserPurchaseInfo) o;

        if (age != that.age) return false;
        if (count != that.count) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (!name.equals(that.name)) return false;
        if (!lastname.equals(that.lastname)) return false;
        if (!purchaseItem.equals(that.purchaseItem)) return false;
        return purchaseDate.equals(that.purchaseDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + name.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + age;
        result = 31 * result + purchaseItem.hashCode();
        result = 31 * result + count;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + purchaseDate.hashCode();
        return result;
    }
}
