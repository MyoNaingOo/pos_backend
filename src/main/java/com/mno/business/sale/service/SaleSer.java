package com.mno.business.sale.service;


import com.mno.business.Store.service.StoreSer;
import com.mno.business.helper.PageDto;
import com.mno.business.product.Prices.ProPrice;
import com.mno.business.product.Prices.ProPriceRepo;
import com.mno.business.product.Prices.ProPriceSer;
import com.mno.business.product.service.ProductSer;
import com.mno.business.sale.Repo.SaleRepo;
import com.mno.business.sale.dto.SaleDto;
import com.mno.business.sale.dto.SaleProDto;
import com.mno.business.sale.entity.Sale;
import com.mno.business.shop.Shop;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleSer {

    private final SaleRepo saleRepo;
    private final StoreSer storeSer;
    private final ProPriceRepo proPriceRepo;
    private final ProductSer productSer;
    private final ProPriceSer proPriceSer;
    private final UserService userService;

    public SaleDto resSaleDto(Sale sale) {

        List<SaleProDto> saleProDtos = new ArrayList<>();
        sale.getSalePros().forEach(
                salePro -> {

                    ProPrice price = proPriceSer.findById(salePro.getPrice_id());
                    SaleProDto saleProDto = SaleProDto.builder()
                            .bulk(salePro.getBulk())
                            .price(price)
                            .price_id(salePro.getPrice_id())
                            .product_id(salePro.getProduct_id())
                            .product(
                                    productSer.changeProDto(
                                            productSer.getProduct(
                                                    salePro.getProduct_id()
                                            )
                                    )
                            )
                            .build();

                    saleProDtos.add(saleProDto);
                }
        );

        return SaleDto.builder()
                .id(sale.getId())
                .user(userService.responeUser(sale.getUser()))
                .saleProList(saleProDtos)
                .time(sale.getTime())
                .build();

    }

    public List<SaleDto> resSaleDtos(List<Sale> sales) {
        List<SaleDto> saleDtos = new ArrayList<>();
        sales.forEach(
                sale -> {
                    saleDtos.add(resSaleDto(sale));
                }
        );
        return saleDtos;
    }


    public void add(Sale sale) {
        saleRepo.save(sale);
        sale.getSalePros().forEach(salePro -> {
            storeSer.updateBulkForSale(salePro.getProduct_id(), salePro.getBulk(), sale.getShop());
        });
    }


    public Sale getSale(Long id) {
        return saleRepo.findById(id).orElse(null);
    }



    /*
     *shop include start
     *
     * */



    public List<Sale> findAllOfShop(int page_num, Shop shop) {
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findAllByShop(shop,pageable);
    }



    public List<Sale> findByMonthOfShop(int month, int year, int page_num,Shop shop) {
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findByMonthOfShop(month, year,shop.getId(), pageable);
    }



    /*
     *shop include end
     *
     * */


    public List<Sale> findAll(int page_num) {
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findAll(pageable).getContent();
    }

    @Async
    public void delete(Long id) {
        Sale sale = getSale(id);
        sale.getSalePros().forEach(
                salePro -> {
                    storeSer.deleteFormSale(salePro.getProduct_id(), salePro.getBulk(), sale.getShop());
                });
        saleRepo.deleteById(id);
    }



    public List<Sale> findByMonth(int month, int year, int page_num) {
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findByMonth(month, year, pageable);
    }





    public PageDto salesOfShop(Shop shop) {
        int sales = saleRepo.saleOfShop(shop.getId());
        int page_size = sales /20;
        return PageDto.builder().number(sales).page_size(page_size).build();
    }


    public PageDto sales() {
        int sales = saleRepo.sales();
        int page_size = sales /20;
        return PageDto.builder().number(sales).page_size(page_size).build();
    }


    public PageDto pageByMonthOfShop(int month, int year, Shop shop) {
        int number = saleRepo.findCountByMonthOfShop(month,year,shop.getId());
        int page_size = number /20;
        return PageDto.builder().number(number).page_size(page_size).build();
    }



    public PageDto pageByMonth(int month, int year) {
        int number = saleRepo.findCountByMonth(month,year);
        int page_size = number /20;
        return PageDto.builder().number(number).page_size(page_size).build();
    }
}
