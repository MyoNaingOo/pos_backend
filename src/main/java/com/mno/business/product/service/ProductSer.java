package com.mno.business.product.service;


import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.helper.PageDto;
import com.mno.business.image.ImageService;
import com.mno.business.product.Prices.ProPrice;
import com.mno.business.product.Prices.ProPriceSer;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
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
                .code(product.getCode())
                .name(product.getName())
                .user(userService.responeUser(product.getUser()))
                .description(product.getDescription())
                .balance(balance)
                .price(price)
                .build();
    }

    public ProductDto changeProDtoOfShop(Product product, Shop shop) {
        int balance = storeRepo.getProBalanceOfShop(product.getId(), shop.getId()).orElse(0);

        ProPrice price = proPriceSer.LtsProPrice(product.getId());
        return ProductDto.builder()
                .id(product.getId())
                .img(product.getImg())
                .code(product.getCode())
                .name(product.getName())
                .user(userService.responeUser(product.getUser()))
                .description(product.getDescription())
                .balance(balance)
                .price(price)
                .time(product.getTime())
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

    public List<ProductDto> changeListProDtoOfShop(List<Product> products, Shop shop) {
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        products.forEach(
                product -> {
                    productDtos.add(changeProDtoOfShop(product, shop));
                }

        );
        return productDtos;
    }

    public void save(Product product) {
        productRepo.save(product);
    }


    /*
     * shop include start
     *
     * */



    /*
     * shop include end
     *
     * */


    public List<Product> findAll(int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return productRepo.findAll(pageable).getContent();
    }


    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product getProductByCode(String code) {
        return productRepo.findByCode(code).orElse(null);
    }


    @Async
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElse(null);
        assert product != null;
        imageService.deleteByName(product.getImg());
        productRepo.deleteById(id);
    }


    public List<Product> findProduct(int num, String find, int pageSize, boolean desc) {
        Pageable pageable;
        if (desc) {
            pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, 20, Sort.by("id"));
        }
        return productRepo.findByNameContainingAndDescriptionContaining(find, find, pageable);
    }

    public PageDto findProductPage(String find,int pageSize) {
        int number = productRepo.pageOfNameContainingAndDescriptionContaining(find, find);
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();


    }

    public List<ProductDto> findByMonth(int month, int year, int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        List<Product> products = productRepo.findByMonth(month, year, pageable);
        return changeListProDto(products);

    }
    public PageDto findByMonthCount(int pageSize,int month,int year) {
        int products = productRepo.findByMonthCount(month,year);
        int page_number = products / pageSize;
        return PageDto.builder().number(products).page_number(page_number).build();

    }

    public PageDto products(int pageSize) {
        int products = productRepo.productsCount();
        int page_number = products / pageSize;
        return PageDto.builder().number(products).page_number(page_number).build();

    }
}
