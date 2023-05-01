package Ticket.Controller;

import org.flowershop.controller.TicketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketControllerTest {

    TicketController ticketController;

    @BeforeEach
    void SetUp() {

        ticketController = new TicketController();
    }

    @DisplayName("Insert Order")
    @Test
    public void InsertOrder() {
        // arrange
        //ticketController.ticketDataRequest(ticketService, productService);

    }

}
