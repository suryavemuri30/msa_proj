//package com.example.order_service.service;
//
//import com.example.order_service.client.InventoryClient;
//import com.example.order_service.model.Order;
//import com.example.order_service.repository.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//
//import java.util.List;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private InventoryClient inventoryClient;
//
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    public Order getOrderById(Long id) {
//        return orderRepository.findById(id).orElse(null);
//    }
//
//    public Order saveOrder(Order order) {
//        // Check inventory before saving order
//        boolean allInStock = order.getOrderLineItemsList().stream()
//                .allMatch(item -> inventoryClient.isInStock(item.getSkuCode(), item.getQuantity())); // Pass required quantity
//
//        if (!allInStock) {
//            throw new IllegalArgumentException("Product not in stock, please try again later.");
//        }
//
//        return orderRepository.save(order);
//    }
//
//
//    public void deleteOrder(Long id) {
//        orderRepository.deleteById(id);
//    }
//}
package com.example.order_service.service;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.client.ProductClient;
import com.example.order_service.dto.ProductInfo;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private ProductClient productClient;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {


        ProductInfo product = productClient.getProductBySkuCode(order.getSkuCode());
        if (product == null) {
            throw new IllegalArgumentException("Product with SKU Code " + order.getSkuCode() + " not found!");
        }

        // Check if there is enough stock before saving the order
        boolean inStock = inventoryClient.isInStock(order.getSkuCode(), order.getQuantity());

        if (!inStock) {
            throw new IllegalArgumentException("Insufficient stock for SKU: " + order.getSkuCode());
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
