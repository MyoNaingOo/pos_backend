package com.mno.business.product.Prices;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProPriceDto {


    private Long product_id;

    private int org_price;
    private int promo_price;
    private LocalDate date;

}
