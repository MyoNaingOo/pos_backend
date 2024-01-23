package com.mno.business.product.controller;


import com.mno.business.config.JwtService;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import com.mno.business.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductControl {

    private final ProductSer productSer;
    private final JwtService jwtService;


    @PostMapping("add")
    public void AddProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
        User user = jwtService.getuser(request);
        Product product = Product.builder()
                .name(productDto.getName())
                .img(productDto.getImg())
                .description(productDto.getDescription())
                .user(user)
                .time(LocalDateTime.now())
                .build();
        productSer.save(product);
    }


    @GetMapping("products")
    public List<ProductDto> productList() {
        return productSer.productList();
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable("id") Long id) {
        Product product = productSer.getProduct(id);
        return productSer.changeProDto(product);
    }

    @GetMapping("find/{num}/{name}")
    private List<ProductDto> findProduct(@PathVariable("name") String name, @PathVariable("num") int num) {
        return productSer.findByName(name, num);
    }


    @GetMapping("page/{num}")
    private List<ProductDto> findProducts(@PathVariable("num") int num) {
        return productSer.findAll(num);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productSer.deleteProduct(id);
    }

    @GetMapping("findByMonth/{num}")
    public List<ProductDto> findByMonth(@RequestParam("month") int month, @RequestParam("year") int year, @PathVariable("num") int num) {
        return productSer.findByMonth(month, year, num);

    }

}
