package com.mno.business.product.Prices;


import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.service.ProductSer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProPriceDto {

    private Long id;
    private Long product_id;
    private int org_price;
    private int promo_price;
    private int purchase_price;
    private LocalDate date;
    private ProductDto productDto;

    @Autowired
    private ProductSer productSer;




}
