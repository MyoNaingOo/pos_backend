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
    private List<StoreDto> storeOfShop(@PathVariable("num") int num, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.storeListOfShop(num, userInfo.getShop()));
    }

    @GetMapping("page")
    private PageDto pageOfShop(@PathVariable("num") int num, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.storesOfShop(userInfo.getShop());
    }


    @GetMapping("prosBalance/{num}")
    public List<StoreDto> getProductsBalanceOfShop(@PathVariable("num") int num, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsBalanceOfShop(num, userInfo.getShop()));
    }

    @GetMapping("prosBalance/page")
    public PageDto pageOfBalanceAndShop(HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfBalanceAndShop(userInfo.getShop());
    }


    @GetMapping("sold/{num}")
    public List<StoreDto> getProductsSoldOfShop(@PathVariable("num") int num, HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.getProductsSoldOfShop(num, userInfo.getShop()));
    }

    @GetMapping("sold/page")
    public PageDto pageOfSoldAndShop(HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfSoldAndShop(userInfo.getShop());
    }

    @GetMapping("findAllByMonth/{num}")
    private List<StoreDto> getByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num, HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findByMonthAndYearOfShop(month, year, num, userInfo.getShop()));
    }


    @GetMapping("findAllByMonth/page")
    private PageDto pageOfMonthAndShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfMonthAndYearAndShop(month, year, userInfo.getShop());
    }

    @GetMapping("findAllByProduct/{num}")
    private List<StoreDto> getAllByProductOfShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.resStoreDtos(storeSer.findAllByProductOfShop(product_id, num, userInfo.getShop()));
    }


    @GetMapping("findAllByProduct/page")
    private PageDto pageOfProductAndShop(
            @PathVariable("num") int num,
            @RequestParam("product_id") Long product_id,
            HttpServletRequest request) {

        UserInfo userInfo = jwtService.getUserInfo(request);
        return storeSer.pageOfProductAndShop(product_id, userInfo.getShop());
    }

    @GetMapping("findAllByUser/{num}")
    public List<StoreDto> findAllByUser(HttpServletRequest request, @PathVariable("num") int num) {
        User user = jwtService.getuser(request);
        return storeSer.resStoreDtos(storeSer.findAllByUser(user.getId(), num));
    }

    @GetMapping("findAllByUser/page")
    public PageDto pageByUser(HttpServletRequest request, @PathVariable("num") int num) {
        User user = jwtService.getuser(request);
        return storeSer.pageByUser(user.getId());
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        storeSer.deleteStore(id);
    }


}
