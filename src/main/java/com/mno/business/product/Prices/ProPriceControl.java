package com.mno.business.product.Prices;

import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/price")
public class ProPriceControl {

    private final ProPriceSer proPriceSer;
    private final ProductSer productSer;

    @PostMapping("add")
    private void add(@RequestBody ProPriceDto proPriceDto) {
        Product product = productSer.getProduct(proPriceDto.getProduct_id());
        ProPrice proPrice = ProPrice.builder()
                .date(LocalDate.now())
                .product(product)
                .org_price(proPriceDto.getOrg_price())
                .promo_price(proPriceDto.getPromo_price())
                .build();

        proPriceSer.add(proPrice);
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        proPriceSer.deleteById(id);
    }

}
