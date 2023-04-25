package org.flowershop;

import org.flowershop.Controller.TicketController;

public class Main {
    public static void main(String[] args) {

        TicketController ticketController = new TicketController();

        ticketController.ticketDataRequest();

        System.out.println("Hello world!");
    }
}