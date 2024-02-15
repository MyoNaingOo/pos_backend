package com.mno.business.sale.controller;

import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.product.Prices.ProPriceSer;
import com.mno.business.sale.dto.SaleDto;
import com.mno.business.sale.entity.Sale;
import com.mno.business.sale.entity.SalePro;
import com.mno.business.sale.service.SaleSer;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/shop/sale/")
public class SaleControl {

    private final SaleSer saleSer;
    private final JwtService jwtService;
    private final ProPriceSer proPriceSer;


    @PostMapping("add")
    public void addSale(@RequestBody SaleDto saleDto, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        List<SalePro> salePros = new ArrayList<>();

        saleDto.getSaleProList().forEach(
                saleProDto -> {
                    Long proPrice_id = proPriceSer.LtsProPrice(saleProDto.getProduct_id()).getId();
                    SalePro salePro = SalePro.builder()
                            .product_id(saleProDto.getProduct_id())
                            .price_id(proPrice_id)
                            .bulk(saleProDto.getBulk())
                            .build();
                    salePros.add(salePro);
                }
        );
        Sale sale = Sale.builder()
                .user(userInfo.getUser())
                .salePros(salePros)
                .shop(userInfo.getShop())
                .time(LocalDateTime.now())
                .build();
        saleSer.add(sale);

    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        saleSer.delete(id);
    }

    /*
     *shop api start
     *
     * */

//   find by month of shop page num and page

    @GetMapping("findByMonth/{num}")
    private List<SaleDto> findByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return saleSer.resSaleDtos(saleSer.findByMonthOfShop(month, year, num, userInfo.getShop()));
    }

    @GetMapping("findByMonth/page")
    private PageDto pageByMonthOfShop(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return saleSer.pageByMonthOfShop(month, year, userInfo.getShop());
    }


//    sale of shop page num and page

    @GetMapping("page/{num}")
    private List<SaleDto> salesOfShop(@PathVariable("num") int num,
                                      HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return saleSer.resSaleDtos(saleSer.findAllOfShop(num, userInfo.getShop()));
    }

    @GetMapping("page")
    private PageDto pageOfShop(HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return saleSer.salesOfShop(userInfo.getShop());
    }


    /*
     *shop api end
     *
     * */


}
