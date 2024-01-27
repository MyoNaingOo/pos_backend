package com.mno.business.Store.service;


import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.entity.Store;
import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import com.mno.business.shop.Shop;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreSer {

    private final StoreRepo storeRepo;
    private final UserService userService;
    private final ProductSer productSer;


    public StoreDto resStoreDto(Store store) {

        return StoreDto.builder()
                .id(store.getId())
                .shop(store.getShop())
                .product(productSer.changeProDto(store.getProduct()))
                .bulk(store.getBulk())
                .user_shop(true)
                .update_bulk(store.getUpdate_bulk())
                .user(userService.responeUser(store.getUser()))
                .time(store.getTime())
                .build();
    }

    public List<StoreDto> resStoreDtos(List<Store> stores) {
        List<StoreDto> storeDtos = new ArrayList<>();
        stores.forEach(
                store -> {
                    StoreDto storeDto = resStoreDto(store);
                    storeDtos.add(storeDto);
                }
        );
        return storeDtos;
    }


    public void addStore(UserInfo userInfo, StoreDto storeDto) {
        Product product = productSer.getProduct(storeDto.getProduct_id());
        if (storeDto.isUser_shop()) {
            Store store = Store.builder()
                    .product(product)
                    .user(userInfo.getUser())
                    .shop(userInfo.getShop())
                    .bulk(storeDto.getBulk())
                    .update_bulk(0)
                    .time(LocalDateTime.now())
                    .build();
            storeRepo.save(store);
        } else {
            Store store = Store.builder()
                    .product(product)
                    .user(userInfo.getUser())
                    .shop(storeDto.getShop())
                    .bulk(storeDto.getBulk())
                    .update_bulk(0)
                    .time(LocalDateTime.now())
                    .build();
            storeRepo.save(store);
        }
    }


    /*
     * shop include start
     *
     * */


    public List<Store> storeListOfShop(int num, Shop shop) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findAllByShop(shop, pageable);
    }


    public List<Store> findByMonthAndYearOfShop(int month, int year, int num,Shop shop) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findByMonthAndYearAndShop(month, year,shop.getId(), pageable);
    }

    public List<Store> findAllByProductOfShop(Long product_id, int num, Shop shop) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        Product product = productSer.getProduct(product_id);
        return storeRepo.findAllByProductAndShop(product, shop, pageable);

    }

    public List<Store> getProductsBalanceOfShop(int num,Shop shop) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsBalanceOfShop(shop.getId(), pageable);
    }

    public List<Store> getProductsSoldOfShop(int num,Shop shop) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsSoldOfShop(shop.getId(),pageable);
    }


    public Integer getProductBalanceOfShop(Long product_id,Shop shop) {
        return storeRepo.getProBalanceOfShop(product_id,shop.getId()).orElse(0);
    }

    /*
     * shop include end
     *
     * */


    public Optional<Store> getStore(Long id) {
        return storeRepo.findById(id);
    }


    public List<Store> storeList(int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findAll(pageable).getContent();
    }

    public void deleteStore(Long id) {
        Store store = getStore(id).orElse(null);
        assert store != null;
        if (0 >= store.getUpdate_bulk()) {
            storeRepo.deleteById(id);
        }
    }

    public List<Store> findByMonthAndYear(int month, int year, int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findByMonthAndYear(month, year, pageable);
    }


    public List<Store> findAllByProduct(Long product_id, int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        Product product = productSer.getProduct(product_id);
        return storeRepo.findAllByProduct(product, pageable);
    }

    public void updateBulkForSale(Long product_id, int updateBulk,Shop shop) {

        Store available = storeRepo.findAvailable(product_id, shop.getId()).orElse(null);
        if (available != null) {
            int fistStore = available.getBulk() - available.getUpdate_bulk();
            if (fistStore < updateBulk) {
                storeRepo.changeUpdateBulk(available.getId(), available.getUpdate_bulk() + fistStore);
                updateBulkForSale(product_id, updateBulk - fistStore,shop);
            } else {
                storeRepo.changeUpdateBulk(available.getId(), available.getUpdate_bulk() + updateBulk);
            }
        }
    }


    public void deleteFormSale(Long product_id, int delete_bulk,Shop shop) {
        Store available = storeRepo.findNowUseStatusByProduct(product_id,shop.getId()).orElse(null);
        if (available != null) {

            if (delete_bulk > available.getUpdate_bulk()) {
                int secondStore = delete_bulk - available.getUpdate_bulk();
                storeRepo.changeUpdateBulk(available.getId(), 0);
                deleteFormSale(product_id, secondStore,shop);
            } else {
                int max_delete = available.getUpdate_bulk() - delete_bulk;
                storeRepo.changeUpdateBulk(available.getId(), max_delete);
            }
        }
    }

    public Integer getProductBalance(Long product_id) {
        return storeRepo.getProBalance(product_id).orElse(0);
    }


    public List<Store> findAllByUser(Long user_id, int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        User user = userService.getUser(user_id).orElse(null);

        return storeRepo.findAllByUser(user, pageable);
    }

    public List<Store> getProductsBalance(int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsBalance(pageable);
    }

    public List<Store> getProductsSold(int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsSold(pageable);
    }
}
