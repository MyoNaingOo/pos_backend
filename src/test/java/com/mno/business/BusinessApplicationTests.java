package com.mno.business;


import com.mno.business.product.entity.Product;
import com.mno.business.product.service.ProductSer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
class BusinessApplicationTests {

	@Autowired
	private ProductSer productSer;

	@Test
	void contextLoads() {

		List<Product> pro = productSer.findAll(0,20,true,"");
		pro.forEach(
				product -> {
					System.out.printf(product.toString() + "\n" );
				}
		);

	}

}
