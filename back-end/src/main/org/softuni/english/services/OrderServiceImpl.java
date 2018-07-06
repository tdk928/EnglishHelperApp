package org.softuni.english.services;

import org.softuni.english.entities.Order;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Watch;
import org.softuni.english.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserServiceImpl userService;

    private final WatchServiceImpl watchService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService, WatchServiceImpl watchService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.watchService = watchService;
    }

    @Override
    public boolean createOrder(String watchId, String username) {
        User user = this.userService.findUserEntityByUsername(username);
        Watch watch = this.watchService.findWatchEntityById(watchId);
        LocalDate currentDate = LocalDate.now();

        Order order = new Order(currentDate, watch, user);
        this.orderRepository.save(order);

        return true;
    }
}
