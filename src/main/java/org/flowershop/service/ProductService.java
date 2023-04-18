package org.flowershop.service;

import org.flowershop.domain.products.Product;
import org.flowershop.repository.IProductRepository;


public class ProductService {
    private IProductRepository repository;


    // This method gets the repository by injection dependencies.
    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }


    // Methods
    public void addProduct(Product product) {
        repository.addProduct(product);
    }

    public void removeProduct(Product product) {
        repository.removeProduct(product);
    }

    public void getProductByName(String name) {
        repository.getProductByName(name);
    }

    public void getProductById(long id) {
        repository.getProductById(id);
    }

    public void displayProducts() {
        repository.displayProducts();
    }

    public void displayStock() {
        repository.displayStock();
    }

}
