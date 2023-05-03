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

    /**
     *
     * @param ticket Ticket for add in file
     * @return The ticket added
     * @throws IOException
     */
    public Ticket addTicket(Ticket ticket) throws IOException {
        ticketRepositoryTXT.addTicket(ticket);
        return ticket;
    }

    /**
     *
     * @return all of tickets in de file
     */
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepositoryTXT.getAllTickets();
        return tickets;
    }

    /**
     *
     * @param id ticket id to search
     * @return. Ticket found
     */
    public Ticket getTicketById(long id) {

        return ticketRepositoryTXT.getTicketById(id);
    }

    /**
     *
     * @return the last ID
     */
    public Long getLastTicketId() {
        return ticketRepositoryTXT.getLastTicketId();
    }

    /**
     *
     * @param id with the id ticket to remove
     * @return the ticket removed
     * @throws IOException
     */
    public Ticket removeTicketById(long id) throws IOException {
        return ticketRepositoryTXT.removeTicketById(id);
    }
}
