package com.mno.business.sale.dto;

import com.mno.business.product.Prices.ProPrice;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleProDto {

    private Long product_id;
    private Long price_id;
    private ProductDto product;
    private ProPrice price;
    private int bulk;
}
