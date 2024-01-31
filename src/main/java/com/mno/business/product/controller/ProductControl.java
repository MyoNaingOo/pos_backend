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
@RequestMapping("api/v2/product")
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


    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable("id") Long id) {
        Product product = productSer.getProduct(id);
        return productSer.changeProDto(product);
    }

    /*
     *shop api start
     *
     * */

    @GetMapping("shop/page/{num}")
    private List<ProductDto> productsOfShop(@PathVariable("num") int num, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return productSer.changeListProDtoOfShop(productSer.findAll(num), userInfo.getShop());
    }

    @GetMapping("shop/pid/{id}")
    public ProductDto getProductOfShop(@PathVariable("id") Long id, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        Product product = productSer.getProduct(id);
        return productSer.changeProDtoOfShop(product, userInfo.getShop());
    }


    @GetMapping("shop/find/code/{code}")
    public ProductDto getProductByCodeOfShop(@PathVariable("code") Long code, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        Product product = productSer.getProductByCode(code);
        return productSer.changeProDtoOfShop(product, userInfo.getShop());
    }

    @GetMapping("shop/find/{value}/{num}")
    private List<ProductDto> findProductOfShop(@PathVariable("value") String value, @PathVariable("num") int num, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return productSer.changeListProDtoOfShop(productSer.findProduct(num, value), userInfo.getShop());
    }



    /*
     *shop api end
     *
     * */

    @GetMapping("find/name/{num}/{name}")
    private List<ProductDto> findProductByName(@PathVariable("name") String name, @PathVariable("num") int num) {
        return productSer.findByName(name, num);
    }


    @GetMapping("find/code/{code}")
    public ProductDto getProductByCode(@PathVariable("code") Long code) {
        Product product = productSer.getProductByCode(code);
        return productSer.changeProDto(product);
    }


    @GetMapping("find/{value}/{num}")
    private List<ProductDto> findProduct(@PathVariable("value") String value, @PathVariable("num") int num) {
        return productSer.changeListProDto(productSer.findProduct(num, value));
    }


    @GetMapping("page/{num}")
    private List<ProductDto> products(@PathVariable("num") int num) {
        return productSer.changeListProDto(productSer.findAll(num));
    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productSer.deleteProduct(id);
    }

    @GetMapping("findByMonth/{num}")
    public List<ProductDto> findByMonth(@RequestParam("month") int month, @RequestParam("year") int year, @PathVariable("num") int num) {
        return productSer.findByMonth(month, year, num);

    }

    @GetMapping("products")
    private PageDto products(){
        return productSer.products();
    }

}
