package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.repositories.OrderRepository;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestHighLevelClient client;

    public void createOrder(Order order){
        orderRepository.save(order);
    }

    public Order findOrderById(String id){
        List<Order> orderList = getAllOrders();
        for(Order order: orderList){
            if(order.getId().equals(id)){
                return order;
            }
        }
        return null;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAllBy();
    }

    public List<Order> getOrdersByCustomer(String id){
        return orderRepository.findByUserId(id);
    }

    public List<Order> getCompletedOrdersByCustomer(String userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.removeIf(order -> !order.getOrderStatus().equals("Completed"));
        return orders;
    }

    public List<Order> getPendingOrdersByCustomer(String userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.removeIf(order -> order.getOrderStatus().equals("Completed"));
        return orders;
    }

    public long getOrderTotalPrice(List<Cart> cartList){
        long totalPrice = 0;
        for(Cart cart: cartList){
            totalPrice = totalPrice  + cart.getBook().getBookPrice() * cart.getQuantity();
        }
        return totalPrice;
    }

    public List<Book> getPurchasedBooks(String userId){
        List<Book> bookList = new ArrayList<>();
        for(Order order: getCompletedOrdersByCustomer(userId)){
            for(Cart cart: order.getCartItems()){
                bookList.add(cart.getBook());
            }
        }
        return bookList;
    }

    public List<Order> getPendingOrders(){
        List<Order> orderList = getAllOrders();
        orderList.removeIf(order -> !order.getOrderStatus().equals("Pending confirmation"));
        int maxIndex = 5;
        if(maxIndex > orderList.size()){
            maxIndex = orderList.size();
        }
        return orderList.stream().sorted(Comparator.comparing(Order::getTransactionDate).reversed()).collect(Collectors.toList()).subList(0, maxIndex);
    }

    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }

    public void confirmOrder(String orderId){
        UpdateRequest updateRequest = new UpdateRequest("order", orderId).doc("orderStatus", "Completed");
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        RefreshRequest request = new RefreshRequest("order");
        try {
            client.indices().refresh(request, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public int getNumberOfBooksSold(String bookId){
        int sold = 0;
        for(Order order: getAllOrders()){
            for(Cart cart: order.getCartItems()){
                if(cart.getBook().getId().equals(bookId)){
                    sold++;
                }
            }
        }
        return sold;
    }
}
