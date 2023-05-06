package org.flowershop.utils;

import org.flowershop.domain.products.Product;

import java.time.LocalDate;
import java.util.List;

public class UtilsQueryTicket {
    private LocalDate date;
    private List<Product> products;

    public UtilsQueryTicket(LocalDate date, List<Product> products) {
        this.date = date;
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
