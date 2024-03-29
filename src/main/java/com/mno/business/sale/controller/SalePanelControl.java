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
import java.util.Locale;

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
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.resSaleDtos(saleSer.findByMonthOfShop(month, year, num, shop, pageSize, desc, perishable.toLowerCase()));
    }


    @GetMapping("shop/findByMonth/page")
    private PageDto pageByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.pageByMonthOfShop(month, year, shop, pageSize, perishable.toLowerCase());
    }


    @GetMapping("shop/page/{num}")
    private List<SaleDto> salesOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.resSaleDtos(saleSer.findAllOfShop(num, shop, pageSize, desc, perishable.toLowerCase()));
    }


    @GetMapping("shop/page")
    private PageDto pageOfShop(
            @RequestParam("shop_id") Long shop_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable
    ) {
        Shop shop = shopSer.shop(shop_id);
        return saleSer.salesOfShop(shop, pageSize, perishable.toLowerCase(Locale.ROOT));
    }


// shop end

    @GetMapping("findByMonth/{num}")
    private List<SaleDto> findByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @PathVariable("num") int num) {
        return saleSer.resSaleDtos(saleSer.findByMonth(month, year, num, pageSize, desc, perishable));
    }


    @GetMapping("findByMonth/page")
    private PageDto pageByMonth(
            @RequestParam("month") int month,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @RequestParam("year") int year) {
        return saleSer.pageByMonth(month, year, pageSize, perishable);
    }


    @GetMapping("page/{num}")
    private List<SaleDto> sales(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc) {
        return saleSer.resSaleDtos(saleSer.findAll(num, pageSize, desc, perishable));
    }

    @GetMapping("page")
    private PageDto page(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "perishable", defaultValue = "all", required = false) String perishable
    ) {
        return saleSer.sales(pageSize, perishable);
    }


}
