package org.flowershop.domain.products;


public class Tree extends Product {
    private float height;


    public Tree() {}

    public Tree(String ref, String name, double price, float height) {
        super(ref, name, price);
        this.height = height;
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    /**
     * This method displays the product information by screen.
     *
     */
    @Override
    public void displayProduct() {
        final StringBuilder sb = new StringBuilder("Tree");
        sb.append("    id: ").append(getId()).append(", ");
        sb.append("    ref: ").append(getRef()).append(", ");
        sb.append("    name: ").append(getName()).append(", ");
        sb.append("    height: ").append(height).append(", ");
        sb.append("    price: ").append(getPrice()).append(", ");
        sb.append("    stock: ").append(getStock());
        System.out.println(sb);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tree{");
        sb.append("id: ").append(getId()).append(", ");
        sb.append("ref: ").append(getRef()).append(", ");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("height: ").append(height).append(", ");
        sb.append("price: ").append(getPrice()).append(", ");
        sb.append("stock: ").append(getStock());
        sb.append('}');
        return sb.toString();
    }

}
