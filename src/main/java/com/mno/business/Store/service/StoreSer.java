package com.mno.business.Store.service;


import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.entity.Store;
import com.mno.business.helper.PageDto;
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
                .quantity(store.getQuantity())
                .update_quantity(store.getUpdate_quantity())
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
            Store store = Store.builder()
                    .product(product)
                    .user(userInfo.getUser())
                    .shop(userInfo.getShop())
                    .quantity(storeDto.getQuantity())
                    .update_quantity(0)
                    .time(LocalDateTime.now())
                    .build();
            storeRepo.save(store);
    }


    /*
     * shop include start
     *
     * */


    public List<Store> storeListOfShop(int num, Shop shop, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.findAllByShop(shop, pageable);
    }


    public List<Store> findByMonthAndYearOfShop(int month, int year, int num, Shop shop, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.findByMonthAndYearAndShop(month, year, shop.getId(), pageable);
    }

    public List<Store> findAllByProductOfShop(Long product_id, int num, Shop shop, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        Product product = productSer.getProduct(product_id);
        return storeRepo.findAllByProductAndShop(product, shop, pageable);

    }

    public List<Store> getProductsBalanceOfShop(int num, Shop shop, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.getProductsBalanceOfShop(shop.getId(), pageable);
    }

    public List<Store> getProductsSoldOfShop(int num, Shop shop, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.getProductsSoldOfShop(shop.getId(), pageable);
    }


    public Integer getProductBalanceOfShop(Long product_id, Shop shop) {
        return storeRepo.getProBalanceOfShop(product_id, shop.getId()).orElse(0);
    }

    /*
     * shop include end
     *
     * */


    public Optional<Store> getStore(Long id) {
        return storeRepo.findById(id);
    }


    public List<Store> storeList(int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.findAll(pageable).getContent();
    }

    public void deleteStore(Long id) {
        Store store = getStore(id).orElse(null);
        assert store != null;
        if (0 >= store.getUpdate_quantity()) {
            storeRepo.deleteById(id);
        }
    }

    public List<Store> findByMonthAndYear(int month, int year, int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.findByMonthAndYear(month, year, pageable);
    }


    public List<Store> findAllByProduct(Long product_id, int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        Product product = productSer.getProduct(product_id);
        return storeRepo.findAllByProduct(product, pageable);
    }

    public void updateBulkForSale(Long product_id, int updateBulk, Shop shop) {

        Store available = storeRepo.findAvailable(product_id, shop.getId()).orElse(null);
        if (available != null) {
            int fistStore = available.getQuantity() - available.getUpdate_quantity();
            if (fistStore < updateBulk) {
                storeRepo.changeUpdateQuantity(available.getId(), available.getUpdate_quantity() + fistStore);
                updateBulkForSale(product_id, updateBulk - fistStore, shop);
            } else {
                storeRepo.changeUpdateQuantity(available.getId(), available.getUpdate_quantity() + updateBulk);
            }
        }
    }


    public void deleteFormSale(Long product_id, int delete_bulk, Shop shop) {
        Store available = storeRepo.findNowUseStatusByProduct(product_id, shop.getId()).orElse(null);
        if (available != null) {

            if (delete_bulk > available.getUpdate_quantity()) {
                int secondStore = delete_bulk - available.getUpdate_quantity();
                storeRepo.changeUpdateQuantity(available.getId(), 0);
                deleteFormSale(product_id, secondStore, shop);
            } else {
                int max_delete = available.getUpdate_quantity() - delete_bulk;
                storeRepo.changeUpdateQuantity(available.getId(), max_delete);
            }
        }
    }


    public List<Store> findAllByUser(Long user_id, int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        User user = userService.getUser(user_id).orElse(null);

        return storeRepo.findAllByUser(user, pageable);
    }

    public List<Store> getProductsBalance(int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.getProductsBalance(pageable);
    }

    public List<Store> getProductsSold(int num, int pageSize, boolean desc) {
        PageRequest pageable;
        if (desc) {
            pageable = PageRequest.of(num, pageSize, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(num, pageSize, Sort.by("id"));
        }
        return storeRepo.getProductsSold(pageable);
    }


//    page

    public PageDto stores(int pageSize) {

        int stores = storeRepo.stores();
        int page_number = stores / pageSize;
        return PageDto.builder().number(stores).page_number(page_number).build();
    }

    public PageDto storesOfShop(Shop shop,int pageSize) {
        int number = storeRepo.storesOfShop(shop.getId());
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageOfBalance(int pageSize) {
        int number = storeRepo.pageOfBalance();
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }


    public PageDto pageOfBalanceAndShop(Shop shop,int pageSize) {
        int number = storeRepo.pageOfBalanceAndShop(shop.getId());
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }


    public PageDto pageOfSold(int pageSize) {
        int number = storeRepo.pageOfSold();
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageOfSoldAndShop(Shop shop,int pageSize) {
        int number = storeRepo.pageOfSoldAndShop(shop.getId());
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }


    public PageDto pageByProduct(Long product_id,int pageSize) {
        int number = storeRepo.pageOfProduct(product_id);
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageOfProductAndShop(Long product_id, Shop shop,int pageSize) {
        int number = storeRepo.pageOfProductAndShop(product_id, shop.getId());
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageOfMonthAndYear(int month, int year,int pageSize) {
        int number = storeRepo.pageOfMonthAndYear(month, year);
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageOfMonthAndYearAndShop(int month, int year, Shop shop,int pageSize) {
        int number = storeRepo.pageOfMonthAndYearAndShop(month, year, shop.getId());
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }

    public PageDto pageByUser(Long userId,int pageSize) {
        int number = storeRepo.pageOfUser(userId);
        int page_number = number / pageSize;
        return PageDto.builder().number(number).page_number(page_number).build();
    }
}
