package com.example.order_service.client;

import com.example.product.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8080/products")
public interface ProductClient {
    @GetMapping("/sku/{skuCode}")
    Product getProductBySkuCode(@PathVariable("skuCode") String skuCode);
}
