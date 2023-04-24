package org.flowershop.domain.tickets;

public class TicketDetail {

    private Long id;
    private Long idProduct;
    private Integer quantity;
    private Double price;
    private Double amount;

    public TicketDetail(Long idProduct, Integer quantity, Double price, Double amount) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }

    public TicketDetail() {
    }


    public Long getId() {
        return id;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TicketDetail{" +
                "idProduct=" + idProduct +
                ", quantity=" + quantity +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
