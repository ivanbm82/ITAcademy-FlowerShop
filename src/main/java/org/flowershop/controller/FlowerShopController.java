package org.flowershop.controller;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.FlowerShopRepositoryTXT;
import org.flowershop.service.FlowerShopService;
import org.flowershop.utils.MenuFlowerShop;
import org.flowershop.utils.Scan.Scan;

import java.util.List;


public class FlowerShopController {
    FlowerShopService flowerShopService;
    ProductController productController;
    TicketController ticketController;


    public FlowerShopController() {
        flowerShopService = new FlowerShopService( new FlowerShopRepositoryTXT());
        productController = new ProductController();
        ticketController = new TicketController();
    }


    public void flowerShopHandleRequest() {
        MenuFlowerShop flowerShopMenu = new MenuFlowerShop();
        boolean exit = false;
        int option;

        do {
            option = flowerShopMenu.menu(loadFlowerShop().getName());
            switch (option) {
                case 0 -> exit = true;
                case 1 -> productController.stockHandleRequest();
                case 2 -> productController.productHandleRequest();
                case 3 -> ticketController.ticketDataRequest();
                case 4 -> financialHandleRequest();
                default -> System.out.println("Choose an option:");
            }
            System.out.println();
        } while(!exit);

    }

    public void financialHandleRequest() {
        MenuFlowerShop menuFlowerShop = new MenuFlowerShop();
        boolean exit = false;
        int option;

        do {
            option = menuFlowerShop.menuFinancial("Financial balance");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> getTotalStoreValue();
                case 2 -> showProfits();
                case 3 -> showAllTickets();
                default -> System.out.println("Choose an option.");
            }
            Scan.askForString("Press enter to continue...");
            System.out.println();
        } while(!exit);

    }


    public List<FlowerShop> load() {
        return flowerShopService.getFlowerShops();
    }

    public FlowerShop loadFlowerShop() {
        return flowerShopService.getFlowerShops().get(0);
    }

    public void add(FlowerShop flowerShop) {
        flowerShopService.addFlowerShop(flowerShop);
    }

    public void getTotalStoreValue() {
        double totalValue = productController.getTotalStoreValue();
        System.out.println("Total flower shop value: " + totalValue);
    }

    public void showAllTickets() {
        List<Ticket> tickets = ticketController.getAllTickets();
        ticketController.showObjectList(tickets);
    }

    public void showProfits() {
        List<Ticket> tickets = ticketController.getAllTickets();
        double profits = tickets.stream()
                .filter(Ticket::getFinished)
                .mapToDouble(Ticket::getAmount)
                .sum();
        System.out.println("Total profits: " + profits);
    }

}
