package com.mno.business.sale.controller;


import com.mno.business.helper.PageDto;
import com.mno.business.product.Prices.ProPriceSer;
import com.mno.business.sale.dto.SaleDto;
import com.mno.business.sale.service.SaleSer;
import com.mno.business.shop.Shop;
import com.mno.business.shop.ShopSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/panel/sale")
public class SalePanelControl {


    private final SaleSer saleSer;
    private final ShopSer shopSer;

// shop start


    @GetMapping("shop/findByMonth/{num}")
    private List<SaleDto> findByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.resSaleDtos(saleSer.findByMonthOfShop(month, year, num, shop));
    }

    @GetMapping("shop/page/{num}")
    private List<SaleDto> salesOfShop(
            @PathVariable("num") int num,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.resSaleDtos(saleSer.findAllOfShop(num, shop));
    }


    @GetMapping("shop/page")
    private PageDto pageOfShop(@RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.salesOfShop(shop);
    }

// shop end

    @GetMapping("findByMonth/{num}")
    private List<SaleDto> findByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num) {
        return saleSer.resSaleDtos(saleSer.findByMonth(month, year, num));
    }

    @GetMapping("page/{num}")
    private List<SaleDto> sales(@PathVariable("num") int num) {
        return saleSer.resSaleDtos(saleSer.findAll(num));
    }


}
