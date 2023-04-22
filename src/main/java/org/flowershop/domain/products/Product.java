package org.flowershop.domain.products;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.flowershop.exceptions.NegativeValueException;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "productType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tree.class, name = "tree"),
        @JsonSubTypes.Type(value = Flower.class, name = "flower"),
        @JsonSubTypes.Type(value = Decoration.class, name = "decoration")
})
public abstract class Product {
    // The last id is the latest id read.
    private static long lastId = 0;
    private long id;
    private String name;
    // The reference code of the product
    private String ref;
    private double price;
    private int stock;


    public Product() {}

    /**
     * Constructor of the abstract class Product.
     * This method includes basic product information such as product reference, name and price.
     *
     * @param ref    The product reference code.
     * @param name   The product's name.
     * @param price  The price of the product.
     */
    public Product(String ref, String name, double price) {
        this.id = getNewId();
        this.ref = ref;
        this.name = name;
        this.price = Math.abs(price);
    }


    // Methods
    private static long getNewId() {
        return ++lastId;
    }

    /**
     * This method validates that the stock value is not negative.
     * If the value is negative, it will throw a custom exception with a informative message.
     *
     * @param stock                    Amount of stock to validate.
     * @return                         If the validation is positive, it returns the value of the stock.
     * @throws NegativeValueException  Reports that the stock value cannot be negative.
     */
    public int checkStock(int stock) throws NegativeValueException {
        if (stock >= 0) {
            return stock;
        } else {
            throw new NegativeValueException("Stock cannot be negative.");
        }
    }

    /**
     * This method validates that the price of the product is a positive value.
     * If the value is negative, it will throw a custom exception with a informative message.
     *
     * @param price                    The price of the product to validate.
     * @return                         If validation is passed, returns the price.
     * @throws NegativeValueException  Reports that the price of the product cannot be negative.
     */
    public double checkPrice(double price) throws NegativeValueException {
        if (price >= 0) {
            return price;
        } else {
            throw new NegativeValueException("The price cannot be negative.");
        }
    }

    /**
     * This method updates the stock quantity of a product by adding or subtracting a quantity.
     *
     * @param quantity
     * @throws NegativeValueException  If the stock tries to update to a negative value.
     */
    public void updateStock(int quantity) throws NegativeValueException {
        int total = getStock() + quantity;
        setStock( checkStock(total) );
    }

    /**
     * Abstract method responsible for displaying product information on the screen.
     */
    public abstract void displayProduct();


    // Getters and Setters
    // This method set the lastId value.
    public static void setLastId(long id) {
        lastId = id;
    }

    public long getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public void setPrice(double price) throws NegativeValueException {
        this.price = checkPrice(price);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws NegativeValueException {
        this.stock = checkStock(stock);
    }

}
