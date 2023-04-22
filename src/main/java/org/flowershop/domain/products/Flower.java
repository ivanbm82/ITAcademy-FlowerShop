package org.flowershop.domain.products;


public class Flower extends Product {
    private String color;


    public  Flower() {}

    public Flower(String ref, String name, double price, String color) {
        super(ref, name, price);
        this.color = color;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    /**
     * This method displays the product information by screen.
     *
     */
    @Override
    public void displayProduct() {
        final StringBuilder sb = new StringBuilder("Flower");
        sb.append("    id: ").append(getId()).append(", ");
        sb.append("    ref: ").append(getRef()).append(", ");
        sb.append("    name: ").append(getName()).append(", ");
        sb.append("    color: '").append(color).append("\', ");
        sb.append("    price: ").append(getPrice()).append(", ");
        sb.append("    stock: ").append(getStock());
        System.out.println(sb);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Flower{");
        sb.append("id: ").append(getId()).append(", ");
        sb.append("ref: ").append(getRef()).append(", ");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("color: '").append(color).append("\', ");
        sb.append("price: ").append(getPrice()).append(", ");
        sb.append("stock: ").append(getStock());
        sb.append('}');
        return sb.toString();
    }

}
