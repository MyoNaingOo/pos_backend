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
    private LocalDate date;
    private ProductDto productDto;

    @Autowired
    private ProductSer productSer;


    public ProPriceDto proPriceDto(ProPrice proPrice) {
        ProductDto productDto = productSer.changeProDto(proPrice.getProduct());
        ProPriceDto proPriceDto = ProPriceDto.builder()
                .id(proPrice.getId())
                .productDto(productDto)
                .org_price(proPrice.getOrg_price())
                .promo_price(proPrice.getPromo_price())
                .date(proPrice.getDate())
                .build();
        return proPriceDto;

    }


    public List<ProPriceDto> proPriceDtos(List<ProPrice> proPrices) {

        List<ProPriceDto> proPricesDtos = new ArrayList<>();
        proPrices.forEach(
                proPrice -> {
                    ProPriceDto proPriceDto = proPriceDto(proPrice);
                    proPricesDtos.add(proPriceDto);
                }
        );
        return proPricesDtos;
    }



}
