package org.flowershop.service;

import org.flowershop.domain.products.Product;
import org.flowershop.repository.IProductRepository;

import java.util.List;


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

    public List<Product> getProducts() {
        return repository.getProducts();
    }

    public Product getProductById(long id) {
        return repository.getProductById(id);
    }

    public Product getProductByRef(String ref) {
         return repository.getProductByRef(ref);
    }

    void updateProductById(long id, Product product) {
        repository.updateProductById(id, product);
    }

    public void removeProductById(long id) {
        repository.removeProductById(id);
    }

    public void removeProductByRef(String ref) {
        repository.getProductByRef(ref);
    }

}
