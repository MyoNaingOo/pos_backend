package com.mno.business.product.controller;


import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import com.mno.business.shop.Shop;
import com.mno.business.shop.ShopSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/panel/product")
@RequiredArgsConstructor
public class ProductPanelControl {

    private final ProductSer productSer;
    private final ShopSer shopSer;


    //    shop start
    @GetMapping("shop/page/{num}")
    private List<ProductDto> productsOfShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return productSer.changeListProDtoOfShop(productSer.findAll(num), shop);
    }

    @GetMapping("shop/pid/{id}")
    public ProductDto getProductOfShop(@PathVariable("id") Long id, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        Product product = productSer.getProduct(id);
        return productSer.changeProDtoOfShop(product, shop);
    }


    @GetMapping("shop/find/code/{code}")
    public ProductDto getProductByCodeOfShop(@PathVariable("code") String code, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        Product product = productSer.getProductByCode(code);
        return productSer.changeProDtoOfShop(product, shop);
    }

    @GetMapping("shop/find/{value}/{num}")
    private List<ProductDto> findProductOfShop(@PathVariable("value") String value, @PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return productSer.changeListProDtoOfShop(productSer.findProduct(num, value), shop);
    }

//    shop end

    @GetMapping("findByMonth/{num}")
    public List<ProductDto> findByMonth(@RequestParam("month") int month, @RequestParam("year") int year, @PathVariable("num") int num) {
        return productSer.findByMonth(month, year, num);
    }


    @GetMapping("find/{value}/{num}")
    private List<ProductDto> findProduct(@PathVariable("value") String value, @PathVariable("num") int num) {
        return productSer.changeListProDto(productSer.findProduct(num, value));
    }

    @GetMapping("page/{num}")
    private List<ProductDto> products(@PathVariable("num") int num) {
        return productSer.changeListProDto(productSer.findAll(num));
    }

}
