package org.flowershop.service;


import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.ITicketRepository;

import java.io.IOException;
import java.util.List;

public class TicketService {
    private static TicketService instance;
    private final ITicketRepository repository;

    private TicketService(ITicketRepository repository) {
        this.repository = repository;
    }

    public static TicketService getInstance(ITicketRepository repository) {
        if (instance == null) {
            instance = new TicketService(repository);
        }
        return instance;
    }


    /**
     *
     * @param ticket Ticket for add in file
     * @return The ticket added
     * @throws IOException
     */
    public Ticket addTicket(Ticket ticket) throws IOException {
        repository.addTicket(ticket);
        return ticket;
    }

    /**
     *
     * @return all of tickets in de file
     */
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = repository.getAllTickets();
        return tickets;
    }

    /**
     *
     * @param id ticket id to search
     * @return. Ticket found
     */
    public Ticket getTicketById(long id) {

        return repository.getTicketById(id);
    }

    /**
     *
     * @return the last ID
     */
    public Long getLastTicketId() {
        return repository.getNewTicketId();
    }

    /**
     *
     * @param id with the id ticket to remove
     * @return the ticket removed
     * @throws IOException
     */
    public Ticket removeTicketById(long id) throws IOException {
        return repository.removeTicketById(id);
    }
}
