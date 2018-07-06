package org.softuni.english.models;

import java.util.List;

public class UserViewModel {
    private String username;

    private List<OrderViewModel> orders;

    public UserViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderViewModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderViewModel> orders) {
        this.orders = orders;
    }
}
