package com.mno.business.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopSer {

    private final ShopRepo shopRepo;

    public void add(Shop shop){
        shopRepo.save(shop);
    }

    public void update(Long id,Shop shop){
        shop.setId(id);
        shopRepo.save(shop);
    }

    public List<Shop> shops(){
        return shopRepo.findAll(Sort.by("region"));
    }

    public Shop shop(Long id){
        return shopRepo.findById(id).orElse(null);
    }


    public void delete(Long id) {
        shopRepo.deleteById(id);
    }
}
