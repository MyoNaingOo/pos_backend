package com.mno.business.product.Prices;

import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProPriceSer {

    private final ProPriceRepo proPriceRepo;

    //    private final ProductSer productSer;
    private final ProductRepo productRepo;

    private final StoreRepo storeRepo;
    private final UserService userService;


    public ProductDto changeProDto(Product product) {
        int balance = storeRepo.getProBalance(product.getId()).orElse(0);
        ProPrice price = LtsProPrice(product.getId());
        return ProductDto.builder()
                .id(product.getId())
                .img(product.getImg())
                .code(product.getCode())
                .name(product.getName())
                .user(userService.responeUser(product.getUser()))
                .description(product.getDescription())
                .balance(balance)
                .price(price)
                .build();
    }


    public ProPriceDto proPriceDto(ProPrice proPrice) {
        ProductDto productDto = changeProDto(proPrice.getProduct());
        return ProPriceDto.builder()
                .id(proPrice.getId())
                .productDto(productDto)
                .org_price(proPrice.getOrg_price())
                .promo_price(proPrice.getPromo_price())
                .date(proPrice.getDate())
                .build();

    }


    public List<ProPriceDto> proPriceDtos(List<ProPrice> proPrices) {

        List<ProPriceDto> proPricesDtos = new ArrayList<>();
        proPrices.forEach(
                proPrice -> {
                    ProPriceDto proPriceDto = proPriceDto(proPrice);
                    proPricesDtos.add(proPriceDto);
                }
        );
        return proPricesDtos;
    }


    public void add(ProPrice proPrice) {
        proPriceRepo.save(proPrice);
    }

    public ProPrice resProPrice(ProPrice proPrice) {

        if (proPrice != null) {

            Product product = Product.builder()
                    .id(proPrice.getProduct().getId())
                    .name(proPrice.getProduct().getName())
                    .build();

            return ProPrice.builder()
                    .id(proPrice.getId())
                    .product(product)
                    .promo_price(proPrice.getPromo_price())
                    .org_price(proPrice.getOrg_price())
                    .date(proPrice.getDate())
                    .build();

        } else {
            return null;
        }

    }


    public ProPrice findById(Long id) {
        return resProPrice(proPriceRepo.findById(id).orElse(null));
    }

    public List<ProPrice> findAllByProduct(Long product_id, int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return proPriceRepo.findAllByProduct(product_id, pageable);
    }

    public List<ProPrice> findAll( int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return proPriceRepo.findAll(pageable).getContent();
    }


    public ProPrice LtsProPrice(Long product_id) {

        return resProPrice(proPriceRepo.findLtsProPrice(product_id).orElse(null));
    }

    public void deleteById(Long id) {
        proPriceRepo.deleteById(id);
    }


}
