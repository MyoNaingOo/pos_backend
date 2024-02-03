package com.mno.business.product.Prices;

import com.mno.business.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProPriceSer {

    private final ProPriceRepo proPriceRepo;


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

    public List<ProPrice> findAllByProduct(Long product_id,int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return proPriceRepo.findAllByProduct(product_id,pageable);
    }

    public ProPrice LtsProPrice(Long product_id) {

        return resProPrice(proPriceRepo.findLtsProPrice(product_id).orElse(null));
    }

    public void deleteById(Long id) {
        proPriceRepo.deleteById(id);
    }


}
