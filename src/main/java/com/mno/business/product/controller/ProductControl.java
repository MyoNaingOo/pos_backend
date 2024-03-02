package com.mno.business.product.controller;


import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v2/shop/product")
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
                .code(productDto.getCode())
                .description(productDto.getDescription())
                .user(user)
                .time(LocalDateTime.now())
                .build();
        productSer.save(product);
    }


    @GetMapping("page/{num}")
    private List<ProductDto> productsOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return productSer.changeListProDtoOfShop(productSer.findAll(num, pageSize, desc), userInfo.getShop());
    }

    @GetMapping("pid/{id}")
    public ProductDto getProductOfShop(@PathVariable("id") Long id, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        Product product = productSer.getProduct(id);
        return productSer.changeProDtoOfShop(product, userInfo.getShop());
    }


    @GetMapping("find/code/{code}")
    public ProductDto getProductByCodeOfShop(@PathVariable("code") String code, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        Product product = productSer.getProductByCode(code);
        return productSer.changeProDtoOfShop(product, userInfo.getShop());
    }

    @GetMapping("find/{value}/{num}")
    private List<ProductDto> findProductOfShop(
            @PathVariable("value") String value,
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return productSer.changeListProDtoOfShop(productSer.findProduct(num, value,pageSize,desc), userInfo.getShop());
    }

    @GetMapping("find/{value}/page")
    private PageDto pageOfFindProductOfShop(
            @PathVariable("value") String value,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest request) {
        return productSer.findProductPage(value,pageSize);

    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productSer.deleteProduct(id);
    }


    @GetMapping("page")
    private PageDto products(
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return productSer.products(pageSize);
    }

}
