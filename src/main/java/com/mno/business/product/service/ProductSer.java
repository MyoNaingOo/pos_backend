package com.mno.business.product.service;


import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.image.ImageService;
import com.mno.business.product.Prices.ProPrice;
import com.mno.business.product.Prices.ProPriceSer;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.user.dto.UserDto;
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
public class ProductSer {

    private final ProductRepo productRepo;
    private final StoreRepo storeRepo;

    private final ProPriceSer proPriceSer;
    private final UserService userService;
    private final ImageService imageService;


    public ProductDto changeProDto(Product product) {
        int balance = storeRepo.getProBalance(product.getId()).orElse(0);

        ProPrice price = proPriceSer.LtsProPrice(product.getId());
        return ProductDto.builder()
                .id(product.getId())
                .img(product.getImg())
                .name(product.getName())
                .user(userService.responeUser(product.getUser()))
                .description(product.getDescription())
                .balance(balance)
                .price(price)
                .build();
    }



    public List<ProductDto> changeListProDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        products.forEach(
                product -> {
                    productDtos.add(changeProDto(product));
                }

        );
        return productDtos;
    }


    public void save(Product product) {
        productRepo.save(product);
    }


    public List<ProductDto> findAll(int num){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        List<Product> products = productRepo.findAll(pageable).getContent();
        return changeListProDto(products);
    }


    public List<ProductDto> productList() {
        return changeListProDto(productRepo.findAll(Sort.by("id").descending()));

    }

    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Async
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElse(null);
        assert product != null;
        imageService.deleteByName(product.getImg());
        productRepo.deleteById(id);
    }


    public List<ProductDto> findByName(String name, int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        List<Product> products = productRepo.findByNameContaining(name, pageable);

        return changeListProDto(products);
    }

    public List<ProductDto> findByMonth(int month,int year,int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        List<Product> products = productRepo.findByMonth(month,year,pageable);
        return changeListProDto(products);

    }


}
