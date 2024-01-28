package com.mno.business.shop;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/shop")
public class ShopController {

    private final ShopSer shopSer;

    @GetMapping("{id}")
    private Shop getShop(@PathVariable("id") Long id){
        return shopSer.shop(id);
    }


    @GetMapping("shops")
    private List<Shop> getShops(){
        return shopSer.shops();
    }

    @PostMapping("add")
    private void add(@RequestBody Shop shop){
        shopSer.add(shop);
    }

    @PutMapping("update/{id}")
    private void updateShop(@PathVariable("id") Long id,@RequestBody Shop shop){
         shopSer.update(id,shop);
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id){
        shopSer.delete(id);
    }



}
