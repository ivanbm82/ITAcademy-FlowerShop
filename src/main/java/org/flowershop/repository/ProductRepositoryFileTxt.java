package org.flowershop.repository;

import org.flowershop.domain.products.Product;

public class ProductRepositoryFileTxt implements IProductRepository {
    private String txtFilename;


    public ProductRepositoryFileTxt(String filename) {
        this.txtFilename = filename;
    }

    @Override
    public void addProduct(Product product) {
        // TODO: Add product to file
    }

    @Override
    public void removeProduct(Product product) {
        // TODO: Remove product
    }

    @Override
    public Product getProductByName(String name) {
        // TODO: get product by name
        return null;
    }

    @Override
    public Product getProductById(long id) {
        // TODO: get product by id
        return null;
    }

    @Override
    public void displayProducts() {
        // TODO: display all products
    }

    @Override
    public void displayStock() {
        // TODO: display the stock
    }
}
