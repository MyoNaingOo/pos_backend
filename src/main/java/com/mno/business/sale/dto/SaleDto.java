package com.mno.business.sale.dto;


import com.mno.business.product.Prices.ProPrice;
import com.mno.business.product.Prices.ProPriceRepo;
import com.mno.business.sale.entity.Sale;
import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SaleDto {

    private Long id;
    private User user;
    private LocalDateTime time;
    private List<SaleProDto> saleProList;


}