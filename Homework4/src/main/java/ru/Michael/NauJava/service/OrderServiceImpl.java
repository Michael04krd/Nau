package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Michael.NauJava.dao.CustomerRepository;
import ru.Michael.NauJava.dao.OrderRepository;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.entity.Customer;
import ru.Michael.NauJava.entity.Order;
import ru.Michael.NauJava.entity.OrderItem;
import ru.Michael.NauJava.entity.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Реализация сервиса заказов
// Работает транзакционно

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Long customerId, List<OrderItemRequest> items) {
        // Находим покупателя
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Создаём заказ
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(new ArrayList<>());

        double totalAmount = 0.0;

        // Создаём позиции заказа
        for (OrderItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.productId()));

            // Есть ли на складе
            if (product.getQuantity() < itemRequest.quantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName() +
                        ". Available: " + product.getQuantity() + ", requested: " + itemRequest.quantity());
            }

            // Уменьшили на складе
            product.setQuantity(product.getQuantity() - itemRequest.quantity());
            productRepository.save(product);

            // Создали позицию
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setPriceAtMoment(product.getPrice());
            item.setOrder(order);

            order.getItems().add(item);
            totalAmount += product.getPrice() * itemRequest.quantity();
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
}