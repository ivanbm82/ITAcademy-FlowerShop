package org.flowershop.repository;

import org.flowershop.domain.products.Product;

import java.util.List;


public interface IProductRepository {
    void addProduct(Product product);
    Product getProductById(long id);
    Product getProductByRef(String ref);
    List<Product> getProducts();
    void updateProductById(long id, Product product);
    void removeProductById(long id);
    void removeProductByRef(String ref);
    void saveProducts();
    void loadProducts();
}
