package org.flowershop.controller;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.repositoryMongoDB.MongoDbRepository;
import org.flowershop.repository.repositorySQL.FlowerShopRepositorySQL;
import org.flowershop.repository.repositorySQL.ProductRepositorySQL;
import org.flowershop.repository.repositorySQL.TicketRepositorySQL;
import org.flowershop.repository.repositoryTXT.FlowerShopRepositoryTXT;
import org.flowershop.repository.repositoryTXT.ProductRepositoryTXT;
import org.flowershop.repository.repositoryTXT.TicketRepositoryTXT;
import org.flowershop.service.FlowerShopService;
import org.flowershop.utils.menu.MenuFlowerShop;
import org.flowershop.utils.Scan.Scan;

import java.util.List;


public class FlowerShopController {
    private FlowerShopService flowerShopService;
    private ProductController productController;
    private TicketController ticketController;


    public FlowerShopController(int typePersistence) {
        // Select the type of persistence.
        switch (typePersistence) {
            case 1: // TXT
                flowerShopService = FlowerShopService.getInstance(FlowerShopRepositoryTXT.getInstance());
                productController = ProductController.getInstance(ProductRepositoryTXT.getInstance());
                ticketController = TicketController.getInstance(ProductRepositoryTXT.getInstance(),
                        TicketRepositoryTXT.getInstance());
                break;
            case 2: // MYSQL
                ProductRepositorySQL productRepositorySQL = new ProductRepositorySQL();
                TicketRepositorySQL ticketRepositorySQL = new TicketRepositorySQL();
                flowerShopService = FlowerShopService.getInstance( new FlowerShopRepositorySQL() );
                productController = ProductController.getInstance( new ProductRepositorySQL() );
                ticketController = TicketController.getInstance( productRepositorySQL,
                        ticketRepositorySQL);
                break;
            case 3: // MONGODB
                MongoDbRepository mongoDbRepository = new MongoDbRepository();
                flowerShopService = FlowerShopService.getInstance( mongoDbRepository );
                productController = ProductController.getInstance( mongoDbRepository );
                ticketController = TicketController.getInstance( mongoDbRepository, mongoDbRepository );
                break;
            default:
                System.out.println("No correct persistence option.");
                break;
        }

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
            //System.out.println();
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

    /**
     * This method prints the total flower shop value.
     */
    public void getTotalStoreValue() {
        double totalValue = productController.getTotalStoreValue();
        System.out.println("Total flower shop value: " + totalValue);
    }

    /**
     * This method prints all historic tickets
     */
    public void showAllTickets() {
        List<Ticket> tickets = ticketController.getAllTickets();
        ticketController.showObjectList(tickets);
    }

    /**
     * This method shows total profits of the flower shop
     */
    public void showProfits() {
        List<Ticket> tickets = ticketController.getAllTickets();
        double profits = tickets.stream()
                .filter(Ticket::getFinished)
                .mapToDouble(Ticket::getAmount)
                .sum();
        System.out.println("Total profits: " + profits);
    }

}
