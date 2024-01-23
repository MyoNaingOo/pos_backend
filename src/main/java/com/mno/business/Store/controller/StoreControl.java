package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.config.JwtService;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/store")
@RequiredArgsConstructor
public class StoreControl {

    private final JwtService jwtService;
    private final StoreSer storeSer;
    private final ProductRepo productRepo;


    @PostMapping("add")
    private void addStore(@RequestBody StoreDto storeDto, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        storeSer.addStore(userInfo, storeDto);
    }



    /*
     *  shop api start
     *
     * */



    @GetMapping("shop/page/{num}")
    private List<StoreDto> pageOfShop(@PathVariable("num") int id,HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(id,userInfo.getShop()));
    }

    @GetMapping("shop/prosBalance/{num}")
    public List<StoreDto> getProductsBalanceOfShop(@PathVariable("num") int num,HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsBalanceOfShop(num,userInfo.getShop()));
    }

    @GetMapping("shop/sold/{num}")
    public List<StoreDto> getProductsSoldOfShop(@PathVariable("num") int num,HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsSoldOfShop(num,userInfo.getShop()));
    }


    @GetMapping("shop/findAllByMonth/{num}")
    private List<StoreDto> getByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findByMonthAndYearOfShop(month, year, num,userInfo.getShop()));
    }


    @GetMapping("shop/findAllByProduct/{num}")
    private List<StoreDto> getAllByProductOfShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findAllByProductOfShop(product_id, num,userInfo.getShop()));
    }






    /*
     *  shop api end
     *
     * */

    /*
     *  Store All not shop
     *
     *
     * */


    @GetMapping("page/{num}")
    private List<StoreDto> page(@PathVariable("num") int id) {
        return storeSer.resStoreDtos(storeSer.storeList(id));
    }

    @GetMapping("prosBalance/{num}")
    public List<StoreDto> getProductsBalance(@PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.getProductsBalance(num));
    }

    @GetMapping("sold/{num}")
    public List<StoreDto> getProductsSold(@PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.getProductsSold(num));
    }


    @GetMapping("findAllByMonth/{num}")
    private List<StoreDto> getByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num) {

        return storeSer.resStoreDtos(storeSer.findByMonthAndYear(month, year, num));
    }


    @GetMapping("findAllByProduct/{num}")
    private List<StoreDto> getAllByProduct(@PathVariable("num") int num, @RequestParam("product_id") Long product_id) {
        return storeSer.resStoreDtos(storeSer.findAllByProduct(product_id, num));
    }

    @GetMapping("findAllByUser/{num}")
    public List<StoreDto> findAllByUser(@RequestParam("user_id") Long user_id, @PathVariable("num") int num) {
        return storeSer.resStoreDtos(storeSer.findAllByUser(user_id, num));
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        storeSer.deleteStore(id);
    }


}
