package org.flowershop.domain.products;

public abstract class Product {
    // id or reference's product
    private static long lastId = getLastId();  // Need to get last id on initialization.
    private long id;
    private String name;
    private double price;
    private int stock;


    public Product(String name, double price) {
        this.id = ++Product.lastId;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    //TODO: This attribute search the last id from persistence method.
    public static long getLastId() {
        return ++Product.lastId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public abstract void displayProduct();
}
