package org.flowershop.domain.products;


import org.flowershop.exceptions.NegativeValueException;

public class Decoration extends Product {
    private String type;


    public Decoration() {}

    public Decoration(String ref, String name, double price, String type) {
        super(ref, name, price);
        this.type = type;
    }


    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * This method displays the product information by screen.
     *
     */
    @Override
    public void displayProduct() {
        final StringBuilder sb = new StringBuilder("Decoration");
        sb.append("    id: ").append(getId()).append(", ");
        sb.append("    ref: ").append(getRef()).append(", ");
        sb.append("    name: ").append(getName()).append(", ");
        sb.append("    color: '").append(type).append("\', ");
        sb.append("    price: ").append(getPrice()).append(", ");
        sb.append("    stock: ").append(getStock());
        System.out.println(sb);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Decoration{");
        sb.append("id: ").append(getId()).append(", ");
        sb.append("ref: ").append(getRef()).append(", ");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("color: '").append(type).append("\', ");
        sb.append("price: ").append(getPrice()).append(", ");
        sb.append("stock: ").append(getStock());
        sb.append('}');
        return sb.toString();
    }

}
