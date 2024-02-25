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
import java.util.Objects;

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
                            .bulk(salePro.getQuantity())
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
            storeSer.updateBulkForSale(salePro.getProduct_id(), salePro.getQuantity(), sale.getShop());
        });
    }


    public Sale getSale(Long id) {
        return saleRepo.findById(id).orElse(null);
    }



    /*
     *shop include start
     *
     * */


    public List<Sale> findAllOfShop(int page_num, Shop shop, int pageSize, boolean desc, String perishable) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id"));
        }

        return switch (perishable) {
            case "true" -> saleRepo.findAllByShopAndPerishable(shop, true, pageable);
            case "false" -> saleRepo.findAllByShopAndPerishable(shop, false, pageable);
            default -> saleRepo.findAllByShop(shop, pageable);
        };
    }


    public List<Sale> findByMonthOfShop(int month, int year, int page_num, Shop shop, int pageSize, boolean desc, String perishable) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id"));
        }

        return switch (perishable) {
            case "true" -> saleRepo.findByMonthOfShopAndPerishable(month, year, shop.getId(), true, pageable);
            case "false" -> saleRepo.findByMonthOfShopAndPerishable(month, year, shop.getId(), false, pageable);
            default -> saleRepo.findByMonthOfShop(month, year, shop.getId(), pageable);
        };

    }



    /*
     *shop include end
     *
     * */


    public List<Sale> findAll(int page_num, int pageSize, boolean desc, String perishable) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id"));
        }


        return switch (perishable) {
            case "true" -> saleRepo.findAllByPerishable(true, pageable);
            case "false" -> saleRepo.findAllByPerishable(false, pageable);
            default -> saleRepo.findAll(pageable).getContent();
        };
    }

    @Async
    public void delete(Long id) {
        Sale sale = getSale(id);
        sale.getSalePros().forEach(
                salePro -> {
                    storeSer.deleteFormSale(salePro.getProduct_id(), salePro.getQuantity(), sale.getShop());
                });
        saleRepo.deleteById(id);
    }


    public List<Sale> findByMonth(int month, int year, int page_num, int pageSize, boolean desc, String perishable) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(page_num, pageSize, Sort.by("id"));
        }

        return switch (perishable) {
            case "true" -> saleRepo.findByMonthAndPerishable(month, year, true, pageable);
            case "false" -> saleRepo.findByMonthAndPerishable(month, year, false, pageable);
            default -> saleRepo.findByMonth(month, year, pageable);
        };


    }

//page


    public PageDto salesOfShop(Shop shop, int pageSize, String perishable) {
        int number;
        switch (perishable) {
            case "true" -> {
                number = saleRepo.saleOfShopAndPerishable(shop.getId(), true);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            case "false" -> {
                number = saleRepo.saleOfShopAndPerishable(shop.getId(), false);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            default -> {
                number = saleRepo.saleOfShop(shop.getId());
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
        }

    }


    public PageDto sales(int pageSize, String perishable) {
        int number;
        switch (perishable) {
            case "true" -> {
                number = saleRepo.salesByPerishable(true);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            case "false" -> {
                number = saleRepo.salesByPerishable(false);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            default -> {
                number = saleRepo.sales();
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
        }

    }


    public PageDto pageByMonthOfShop(int month, int year, Shop shop, int pageSize, String perishable) {

        int number;

        switch (perishable) {
            case "true" -> {
                number = saleRepo.findCountByMonthOfShopAndPerishable(month, year, shop.getId(), true);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            case "false" -> {
                number = saleRepo.findCountByMonthOfShopAndPerishable(month, year, shop.getId(), false);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            default -> {
                number = saleRepo.findCountByMonthOfShop(month, year, shop.getId());
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
        }

    }


    public PageDto pageByMonth(int month, int year, int pageSize, String perishable) {
        int number;
        switch (perishable) {
            case "true" -> {
                number = saleRepo.findCountByMonthAndPerishable(month, year, true);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            case "false" -> {
                number = saleRepo.findCountByMonthAndPerishable(month, year, false);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
            default -> {
                number = saleRepo.findCountByMonth(month, year);
                int page_number = number / pageSize;
                return PageDto.builder().number(number).page_number(page_number).build();
            }
        }


    }


}
