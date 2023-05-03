package org.flowershop.repository;

import org.flowershop.domain.tickets.Ticket;

import java.io.IOException;
import java.util.List;

public interface ITicketRepository {

    public Ticket addTicket(Ticket ticket);
    public Ticket getTicketById(Long id);
    public List<Ticket> getAllTickets();
    public Long getLastTicketId();
    public Ticket removeTicketById(long id) throws IOException;
}
