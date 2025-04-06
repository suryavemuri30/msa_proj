package com.example.order_service.client;

import com.example.order_service.dto.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/sku/{skuCode}")
    ProductInfo getProductBySkuCode(@PathVariable("skuCode") String skuCode);
}
