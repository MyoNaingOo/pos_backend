package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.helper.PageDto;
import com.mno.business.shop.Shop;
import com.mno.business.shop.ShopSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/panel/store")
@RequiredArgsConstructor
public class StorePanelControl {

    private final StoreSer storeSer;
    private final ShopSer shopSer;


    @GetMapping("shop/page/{num}")
    private List<StoreDto> storeOfShop(
            @PathVariable("num") int num,
            @RequestParam("shop_id") Long shop_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(num, shop, pageSize, desc));
    }

    @GetMapping("shop/page")
    private PageDto pageOfShop(
            @RequestParam("shop_id") Long shop_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.storesOfShop(shop, pageSize);
    }


    @GetMapping("shop/prosBalance/{num}")
    public List<StoreDto> getProductsBalanceOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.getProductsBalanceOfShop(num, shop, pageSize, desc));
    }

    @GetMapping("shop/prosBalance/page")
    public PageDto pageOfBalanceAndShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfBalanceAndShop(shop, pageSize);
    }

    @GetMapping("shop/sold/{num}")
    public List<StoreDto> getProductsSoldOfShop(
            @PathVariable("num") int num, @RequestParam("shop_id") Long shop_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.getProductsSoldOfShop(num, shop, pageSize, desc));
    }

    @GetMapping("shop/sold/page")
    public PageDto pageOfSoldAndShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfSoldAndShop(shop, pageSize);
    }


    @GetMapping("shop/findAllByMonth/{num}")
    private List<StoreDto> getByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.findByMonthAndYearOfShop(month, year, num, shop, pageSize, desc));
    }


    @GetMapping("shop/findAllByMonth/page")
    private PageDto pageOfMonthAndShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfMonthAndYearAndShop(month, year, shop, pageSize);
    }


    @GetMapping("shop/findAllByProduct/{num}")
    private List<StoreDto> getAllByProductOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("product_id") Long product_id,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.findAllByProductOfShop(product_id, num, shop, pageSize, desc));
    }

    @GetMapping("shop/findAllByProduct/page")
    private PageDto pageOfProductAndShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam("product_id") Long product_id,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfProductAndShop(product_id, shop, pageSize);
    }

    /*
     *  shop api end
     *
     * */

    @GetMapping("page/{num}")
    private List<StoreDto> page(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        return storeSer.resStoreDtos(storeSer.storeList(num, pageSize, desc));
    }


    @GetMapping("page")
    private PageDto page(@RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        return storeSer.stores(pageSize);
    }


    @GetMapping("prosBalance/{num}")
    public List<StoreDto> getProductsBalance(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        return storeSer.resStoreDtos(storeSer.getProductsBalance(num, pageSize, desc));
    }

    @GetMapping("prosBalance/page")
    public PageDto pageOfBalance(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return storeSer.pageOfBalance(pageSize);
    }

    @GetMapping("sold/{num}")
    public List<StoreDto> getProductsSold(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        return storeSer.resStoreDtos(storeSer.getProductsSold(num, pageSize, desc));
    }

    @GetMapping("sold/page")
    public PageDto pageOfSold(@RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return storeSer.pageOfSold(pageSize);
    }


    @GetMapping("findAllByMonth/{num}")
    private List<StoreDto> getByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @PathVariable("num") int num) {

        return storeSer.resStoreDtos(storeSer.findByMonthAndYear(month, year, num, pageSize, desc));
    }


    @GetMapping("findAllByMonth/page")
    private PageDto pageOfMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize

    ) {

        return storeSer.pageOfMonthAndYear(month, year, pageSize);
    }

    @GetMapping("findAllByProduct/{num}")
    private List<StoreDto> getAllByProduct(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("product_id") Long product_id) {
        return storeSer.resStoreDtos(storeSer.findAllByProduct(product_id, num, pageSize, desc));
    }

    @GetMapping("findAllByProduct/page")
    private PageDto pageOfProduct(@RequestParam("product_id") Long product_id, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return storeSer.pageByProduct(product_id, pageSize);
    }


    @GetMapping("findAllByUser/{num}")
    public List<StoreDto> findAllByUser(
            @RequestParam("user_id") Long user_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.findAllByUser(user_id, num, pageSize, desc));
    }

    @GetMapping("findAllByUser/page")
    public PageDto pageByUser(
            @RequestParam("user_id") Long user_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @PathVariable("num") int num) {
        return storeSer.pageByUser(user_id, pageSize);
    }



    /*
     *  Store All not shop
     *
     *
     * */

}
