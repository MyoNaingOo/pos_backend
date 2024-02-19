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
    private List<StoreDto> storeOfShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(num, shop));
    }

    @GetMapping("shop/page")
    private PageDto pageOfShop(@RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.storesOfShop(shop);
    }


    @GetMapping("shop/prosBalance/{num}")
    public List<StoreDto> getProductsBalanceOfShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.getProductsBalanceOfShop(num, shop));
    }

    @GetMapping("shop/prosBalance/page")
    public PageDto pageOfBalanceAndShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfBalanceAndShop(shop);
    }

    @GetMapping("shop/sold/{num}")
    public List<StoreDto> getProductsSoldOfShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.getProductsSoldOfShop(num, shop));
    }

    @GetMapping("shop/sold/page")
    public PageDto pageOfSoldAndShop(@PathVariable("num") int num, @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfSoldAndShop(shop);
    }


    @GetMapping("shop/findAllByMonth/{num}")
    private List<StoreDto> getByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.findByMonthAndYearOfShop(month, year, num, shop));
    }


    @GetMapping("shop/findAllByMonth/page")
    private PageDto pageOfMonthAndShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfMonthAndYearAndShop(month, year, shop);
    }


    @GetMapping("shop/findAllByProduct/{num}")
    private List<StoreDto> getAllByProductOfShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.resStoreDtos(storeSer.findAllByProductOfShop(product_id, num, shop));
    }

    @GetMapping("shop/findAllByProduct/page")
    private PageDto pageOfProductAndShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        return storeSer.pageOfProductAndShop(product_id, shop);
    }

    /*
     *  shop api end
     *
     * */

    @GetMapping("page/{num}")
    private List<StoreDto> page(@PathVariable("num") int id) {
        return storeSer.resStoreDtos(storeSer.storeList(id));
    }


    @GetMapping("page")
    private PageDto page() {
        return storeSer.stores();
    }


    @GetMapping("prosBalance/{num}")
    public List<StoreDto> getProductsBalance(@PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.getProductsBalance(num));
    }

    @GetMapping("prosBalance/page")
    public PageDto pageOfBalance(@PathVariable("num") int num) {
        return storeSer.pageOfBalance();
    }

    @GetMapping("sold/{num}")
    public List<StoreDto> getProductsSold(@PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.getProductsSold(num));
    }

    @GetMapping("sold/page")
    public PageDto pageOfSold(@PathVariable("num") int num) {
        return storeSer.pageOfSold();
    }


    @GetMapping("findAllByMonth/{num}")
    private List<StoreDto> getByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num) {

        return storeSer.resStoreDtos(storeSer.findByMonthAndYear(month, year, num));
    }


    @GetMapping("findAllByMonth/page")
    private PageDto pageOfMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {

        return storeSer.pageOfMonthAndYear(month, year);
    }

    @GetMapping("findAllByProduct/{num}")
    private List<StoreDto> getAllByProduct(@PathVariable("num") int num, @RequestParam("product_id") Long product_id) {
        return storeSer.resStoreDtos(storeSer.findAllByProduct(product_id, num));
    }

    @GetMapping("findAllByProduct/page")
    private PageDto pageOfProduct(@RequestParam("product_id") Long product_id) {
        return storeSer.pageByProduct(product_id);
    }


    @GetMapping("findAllByUser/{num}")
    public List<StoreDto> findAllByUser(@RequestParam("user_id") Long user_id, @PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.findAllByUser(user_id, num));
    }

    @GetMapping("findAllByUser/page")
    public PageDto pageByUser(@RequestParam("user_id") Long user_id, @PathVariable("num") int num) {
        return storeSer.pageByUser(user_id);
    }



    /*
     *  Store All not shop
     *
     *
     * */

}
