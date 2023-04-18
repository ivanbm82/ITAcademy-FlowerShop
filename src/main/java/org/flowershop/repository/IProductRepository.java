package org.flowershop.repository;

import org.flowershop.domain.products.Product;

public interface IProductRepository {
    void addProduct(Product product);
    void removeProduct(Product product);
    Product getProductByName(String name);
    Product getProductById(long id);
    void displayProducts();
    void displayStock();
}
