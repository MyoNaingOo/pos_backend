package com.mno.business.Store.dto;


import com.mno.business.product.dto.ProductDto;
import com.mno.business.shop.Shop;
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
public class StoreDto {


    private Long id;
    private Long product_id;
    private int quantity;
    private LocalDateTime time;
    private User user;
    private Shop shop;
    private ProductDto product;
    private int update_bulk;
    private boolean user_shop;


}
