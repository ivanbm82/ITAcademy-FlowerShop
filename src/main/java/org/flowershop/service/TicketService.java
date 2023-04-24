package org.flowershop.service;


import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.TicketRepositoryTXT;

import java.io.IOException;
import java.util.List;

public class TicketService {

    private final TicketRepositoryTXT ticketRepositoryTXT;

    public TicketService(TicketRepositoryTXT ticketRepositoryTXT) {
        this.ticketRepositoryTXT = ticketRepositoryTXT;
    }

    public Ticket addTicket(Ticket ticket) throws IOException {
        //¿Check suplicate id lo controlamos aqui?
        ticketRepositoryTXT.addTicket(ticket);
        return ticket;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepositoryTXT.getAllTickets();
        return tickets;
    }

    public Ticket getTicketById(long id) {

        return ticketRepositoryTXT.getTicketById(id);
    }

    public Long getLastTicketId() {
        return ticketRepositoryTXT.getLastTicketId();
    }

    public Ticket removeTicketById(long id) throws IOException {
        return ticketRepositoryTXT.removeTicketById(id);
    }
}
