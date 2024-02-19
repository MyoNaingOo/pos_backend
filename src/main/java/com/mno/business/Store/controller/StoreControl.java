package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.shop.Shop;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/shop/store")
@RequiredArgsConstructor
public class StoreControl {

    private final JwtService jwtService;
    private final StoreSer storeSer;


    @PostMapping("add")
    private void addStore(@RequestBody StoreDto storeDto, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        storeSer.addStore(userInfo, storeDto);
    }



    /*
     *  shop api start
     *
     * */


    @GetMapping("page/{num}")
    private List<StoreDto> storeOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(num, userInfo.getShop(), pageSize, desc));
    }

    @GetMapping("page")
    private PageDto pageOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest request
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.storesOfShop(userInfo.getShop(), pageSize);
    }


    @GetMapping("prosBalance/{num}")
    public List<StoreDto> getProductsBalanceOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsBalanceOfShop(num, userInfo.getShop(), pageSize, desc));
    }

    @GetMapping("prosBalance/page")
    public PageDto pageOfBalanceAndShop(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest request
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfBalanceAndShop(userInfo.getShop(), pageSize);
    }


    @GetMapping("sold/{num}")
    public List<StoreDto> getProductsSoldOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request
    ) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsSoldOfShop(num, userInfo.getShop(), pageSize, desc));
    }

    @GetMapping("sold/page")
    public PageDto pageOfSoldAndShop(HttpServletRequest request, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfSoldAndShop(userInfo.getShop(), pageSize);
    }

    @GetMapping("findAllByMonth/{num}")
    private List<StoreDto> getByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findByMonthAndYearOfShop(month, year, num, userInfo.getShop(), pageSize, desc));
    }


    @GetMapping("findAllByMonth/page")
    private PageDto pageOfMonthAndShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfMonthAndYearAndShop(month, year, userInfo.getShop(), pageSize);
    }

    @GetMapping("findAllByProduct/{num}")
    private List<StoreDto> getAllByProductOfShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findAllByProductOfShop(product_id, num, userInfo.getShop(), pageSize, desc));
    }


    @GetMapping("findAllByProduct/page")
    private PageDto pageOfProductAndShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfProductAndShop(product_id, userInfo.getShop(), pageSize);
    }

    @GetMapping("findAllByUser/{num}")
    public List<StoreDto> findAllByUser(
            HttpServletRequest request,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @PathVariable("num") int num) {
        User user = jwtService.getuser(request);
        return storeSer.resStoreDtos(storeSer.findAllByUser(user.getId(), num, pageSize, desc));
    }

    @GetMapping("findAllByUser/page")
    public PageDto pageByUser(
            HttpServletRequest request,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @PathVariable("num") int num) {
        User user = jwtService.getuser(request);
        return storeSer.pageByUser(user.getId(), pageSize);
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        storeSer.deleteStore(id);
    }


}
