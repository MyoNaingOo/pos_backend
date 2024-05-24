package com.mno.business.product.dto;


import com.mno.business.product.Prices.ProPrice;
import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String img;
    private Long user_id;
    private User user;
    private String code;
    private String description;
    private int balance;
    private String category;
    private ProPrice price;
    private LocalDateTime time;


}
