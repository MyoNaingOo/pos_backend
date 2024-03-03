package com.mno.business.product.Prices;

import com.mno.business.helper.PageDto;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
                .purchase_price(proPriceDto.getPurchase_price())
                .org_price(proPriceDto.getOrg_price())
                .promo_price(proPriceDto.getPromo_price())
                .build();

        proPriceSer.add(proPrice);
    }

    @GetMapping("product/page/{num}")
    private List<ProPriceDto> proPriceByProduct(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("product_id") Long product_id
    ) {
        List<ProPrice> proPrices = proPriceSer.findAllByProduct(product_id, num,pageSize,desc);
        return proPriceSer.proPriceDtos(proPrices);
    }

    @GetMapping("product/page")
    private PageDto proPriceByProductPage(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return proPriceSer.proPricePage(pageSize);
    }

    @GetMapping("page/{num}")
    private List<ProPriceDto> proPrice(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        List<ProPrice> proPrices = proPriceSer.findAll(num,pageSize,desc);
        return proPriceSer.proPriceDtos(proPrices);
    }


    @GetMapping("page")
    private PageDto page(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return proPriceSer.proPricePage(pageSize);
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        proPriceSer.deleteById(id);
    }

}
