package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/store")
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



    @GetMapping("shop/page/{num}")
    private List<StoreDto> pageOfShop(@PathVariable("num") int num,HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(num,userInfo.getShop()));
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

    @GetMapping("shop/page")
    private PageDto pageOfShop(HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.storesOfShop(userInfo.getShop());
    }




    @GetMapping("page")
    private PageDto page() {
        return storeSer.stores();
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        storeSer.deleteStore(id);
    }


}
