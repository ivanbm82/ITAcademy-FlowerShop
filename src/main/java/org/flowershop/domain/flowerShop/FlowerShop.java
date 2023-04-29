package org.flowershop.domain.flowerShop;

public class FlowerShop {
    private static long lastId = 0;
    private long id;
    private String ref;
    private String name;


    public FlowerShop() {}

    public FlowerShop(String ref, String name) {
        this.id = getNextId();
        this.ref = ref;
        this.name = name;
    }

    private static long getNextId() {
        return ++lastId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlowerShop{");
        sb.append("id=").append(id);
        sb.append(", ref='").append(ref).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
