package org.flowershop.service;

import org.flowershop.domain.products.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private List<Product> products = new ArrayList<Product>();


    // Methods
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void getProductByName(String name) {
        // TODO: getProductByName
    }

    public void getProductById(long id) {
        // TODO: getProductById
    }

    public void displayProducts() {
        for (Product product: products) {
            product.displayProduct();
        }
    }

    public void displayStock() {
        int stock = 0;
        for (Product product: products) {
            stock += product.getStock();
        }
        System.out.println("Total FlowerShop stock: " + stock);
    }

}
