package org.flowershop.domain.products;

public class Flower extends Product {
    private String color;


    public Flower(String name, double price, String color) {
        super(name, price);
        this.color = color;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // TODO: Method to display flower info on screen.
    @Override
    public void displayProduct() {
        // TODO: Logic to display a flower product.
        final StringBuilder sb = new StringBuilder("Flower{");
        sb.append("name=").append(getName());
        sb.append("price=").append(getPrice());
        sb.append("color=").append(color);
        sb.append('}');
        System.out.println(sb);
    }

}
