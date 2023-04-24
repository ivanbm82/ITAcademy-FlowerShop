package org.flowershop;

import org.flowershop.domain.tickets.Ticket;
import org.flowershop.domain.tickets.TicketDetail;
import org.flowershop.repository.TicketRepositoryTXT;
import org.flowershop.service.TicketService;

import java.io.IOException;
import java.text.ParseException;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {

        /*
        TicketRepositoryTXT ticketRepositoryTXT = new TicketRepositoryTXT();

        TicketService ticketService = new TicketService(ticketRepositoryTXT);

        Ticket.lastId = ticketService.getLastTicketId();

        Ticket ticket = new Ticket(new Date(), 1L, 345.0, true);
        TicketDetail ticketDetail1 = new TicketDetail(1L,3,100.0,300.0);
        TicketDetail ticketDetail2 = new TicketDetail(2L,1,45.0,45.0);
        ticket.addTicketDetail(ticketDetail1);
        ticket.addTicketDetail(ticketDetail2);
        ticketService.addTicket(ticket);

        Ticket ticket1 = new Ticket(new Date(), 1L, 185.0, true);
        TicketDetail ticketDetail3 = new TicketDetail(1L,5,25.0,125.0);
        TicketDetail ticketDetail4 = new TicketDetail(2L,2,30.0,60.0);
        ticket1.addTicketDetail(ticketDetail3);
        ticket1.addTicketDetail(ticketDetail4);
        ticketService.addTicket(ticket1);

        System.out.println("--- Provant getTicketById ---");
        Ticket ticket2 = ticketService.getTicketById(37L);
        if (ticket2 == null)
            System.out.println("Ticket no encontrado");
        else System.out.println(ticket2);

        System.out.println("--- Provant getAllTickets ---");
        List<Ticket> ticketList = ticketService.getAllTickets();
        ticketList.stream().forEach(System.out::println);

        System.out.println("--- Provant removeTicketById ---");

        Ticket ticket3 = ticketService.removeTicketById(1L);
        if (ticket3 == null)
            System.out.println("Ticket no encontrado");
        else System.out.println("Ticket Borrado: \n" + ticket3);

         */

        System.out.println("Hello world!");
    }
}