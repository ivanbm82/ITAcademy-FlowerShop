package org.flowershop.domain.tickets;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Ticket implements Serializable {

    //public static long lastId = 0;  // Need to get last id on initialization.
    private Long id;
    private Date date;
    private Long client = Long.valueOf(1);
    private Double amount;
    private Boolean finished;

    private List<TicketDetail> ticketDetailList;

    public Ticket(Long id, Date date, Long client, Double amount, Boolean finished) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.amount = amount;
        this.finished = finished;
        this.ticketDetailList = new ArrayList<>();
    }

    public Ticket() {
    }

    public void addTicketDetail(TicketDetail ticketDetail) {
        ticketDetailList.add(ticketDetail);
    }

    public void removeTicketDetail(TicketDetail ticketDetail) {
        ticketDetailList.remove(ticketDetail);
    }

    public void updateTicketDetail(TicketDetail ticketDetail, Integer quantity, Double price) {
        ticketDetail.setQuantity(quantity);
        ticketDetail.setAmount(quantity*price);
        this.amount = calcTotalTicketAmount();
    }

    private Double calcTotalTicketAmount() {

        return this.ticketDetailList.stream().mapToDouble(TicketDetail::getAmount).sum();

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Long getClient() {
        return client;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinishedTrue() {
        this.finished = true;
    }

    public List<TicketDetail> getTicketDetailList() {
        return ticketDetailList;
    }

    public void setTicketDetailList(List<TicketDetail> ticketDetailList) {
        this.ticketDetailList = ticketDetailList;
    }

    @Override
    public String toString() {
        return "Ticket:" +
                "ID= " + id +
                ", date=" + date +
       //         ", client=" + client +
                ", amount=" + amount + "\n" +
        //        ", finished=" + finished +
                ", products=" + ticketDetailList +
                '}';
    }

}
