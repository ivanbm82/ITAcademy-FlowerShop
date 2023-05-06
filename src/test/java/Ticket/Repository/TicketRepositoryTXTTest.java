package Ticket.Repository;

import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.repositoryTXT.TicketRepositoryTXT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketRepositoryTXTTest {

    TicketRepositoryTXT ticketRepositoryTXT;


    @BeforeEach
    void SetUp() {
        ticketRepositoryTXT = TicketRepositoryTXT.getInstance();
    }

    @DisplayName("Adding new Ticket")
    @Test
    public void addTicket() throws IOException {
        // arrange
        /*
        Long id = ticketRepositoryTXT.getLastTicketId();
        Ticket ticket1 = new Ticket(id, new Date(), 1L, "T001", 345.0, true);
        TicketDetail ticketDetail1 = new TicketDetail(1L, 3, 100.0, 300.0);
        TicketDetail ticketDetail2 = new TicketDetail(2L, 1, 45.0, 45.0);
        ticket1.addTicketDetail(ticketDetail1);
        ticket1.addTicketDetail(ticketDetail2);

        Ticket nuevo = ticketRepositoryTXT.addTicket(ticket1);

        assertEquals(ticket1,nuevo);


         */

    }

    @DisplayName("Get All Tickets")
    @Test
    public void getAllTickets() {
        // arrange
        List<Ticket> tickets = ticketRepositoryTXT.getAllTickets();


        //assertEquals(ticket1,nuevo);


    }

    @DisplayName("Remove Ticket")
    @Test
    public void removeTicketById() throws IOException {
        // arrange
        Ticket nuevo = ticketRepositoryTXT.removeTicketById(3L);


        //assertEquals(ticket1,nuevo);


    }


}



