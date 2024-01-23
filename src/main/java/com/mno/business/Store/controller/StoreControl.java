package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.entity.Store;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.config.JwtService;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.entity.Product;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        storeSer.addStore(userInfo,storeDto);
    }





























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
