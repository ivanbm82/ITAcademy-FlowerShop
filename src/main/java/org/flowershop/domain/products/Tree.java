package org.flowershop.domain.products;

public class Tree extends Product {
    private float height;


    public Tree(String name, double price, float height) {
        super(name, price);
        this.height = height;
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    // TODO: Method to display tree on screen.
    @Override
    public void displayProduct() {
        // TODO: Logic to display a tree product.
        final StringBuilder sb = new StringBuilder("Tree{");
        sb.append("name=").append(getName());
        sb.append("price=").append(getPrice());
        sb.append("height=").append(height);
        sb.append('}');
        System.out.println(sb);
    }

}
