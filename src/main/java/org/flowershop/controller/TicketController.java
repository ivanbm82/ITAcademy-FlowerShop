package org.flowershop.controller;

import org.flowershop.domain.products.Product;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.domain.tickets.TicketDetail;
import org.flowershop.repository.ProductRepositoryTXT;
import org.flowershop.repository.TicketRepositoryTXT;
import org.flowershop.service.ProductService;
import org.flowershop.service.TicketService;

import org.flowershop.utils.MenuTickets;
import org.flowershop.utils.Scan.Scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static org.flowershop.utils.Scan.Scan.askForInt;
import static org.flowershop.utils.Scan.Scan.askForString;


public class TicketController {
    Properties properties;
    ProductService productService;
    TicketService ticketService;
    String fileTicket;


    public TicketController() {
        properties = new Properties();
        productService = new ProductService( new ProductRepositoryTXT());
        String directoryProgram = System.getProperty("user.dir");
        String configFile = directoryProgram + "\\src\\main\\resources\\config.properties";
        try {
            properties.load(new FileInputStream(new File(configFile)));
            fileTicket = directoryProgram + "\\" + (String) properties.get("fileTicket");
            ticketService = new TicketService(new TicketRepositoryTXT(fileTicket));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ticketDataRequest() {
        List<Product> products = productService.getProducts();
        List<TicketDetail> ticketDetails = new ArrayList<>();
        Boolean exit = false;

        try {
            do {
                try {
                    int option = MenuTickets.showOption("Tickets");
                    System.out.println(option);
                    switch (option) {
                        case 1:
                            System.out.println("option product");
                            //TODO Adding new product in ticket
                            addProductInTicketDetail(products, ticketDetails);
                            break;
                        case 2:
                            //TODO Modify Quantity from a ticketDetail
                            modifyQuantityInTicketDetail(ticketDetails);
                            break;
                        case 3:
                            //TODO Deleting productDetail from a ticket
                            removeProductInTicketDetail(ticketDetails);
                            break;
                        case 4:
                            //TODO Print all ticketDetail
                            showProductsInTicket(ticketDetails);
                            Scan.askForString("Press enter to continue...");
                            break;
                        case 5:
                            //TODO Save Ticket
                            saveTicket(ticketService, ticketDetails);
                            break;
                        case 6:
                            ticketDetails.clear();
                            exit = true;
                            break;
                        default:
                            System.out.println("Incorrect option");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect option");
                }

            } while (!exit);
        } catch (Exception e) {
            System.out.println("Bye!!!");
        }

    }

    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    private void showProductsInTicket(List<TicketDetail> ticketDetails) {
        showObjectList(ticketDetails);
        DecimalFormat df = new DecimalFormat("#.##");
        Double total = ticketDetails.stream().mapToDouble(TicketDetail::getAmount).sum();
        System.out.println("Total ticket: " + df.format(total));
    }

    private void saveTicket(TicketService ticketService, List<TicketDetail> ticketDetails) throws IOException {
        Double total = ticketDetails.stream().mapToDouble(TicketDetail::getAmount).sum();
        Ticket ticket = new Ticket(ticketService.getLastTicketId(), new Date(), 1L, total, true);
        ticketDetails.stream().forEach(ticket::addTicketDetail);

        ticketService.addTicket(ticket);
        System.out.println(ticket);

        ticketDetails.clear();
    }

    private List<TicketDetail> modifyQuantityInTicketDetail(List<TicketDetail> ticketDetails) {

        if (ticketDetails.size() > 0) {
            System.out.println("Update Product in TicketDetail ...\n");
            showObjectList(ticketDetails);

            String reference = askForString("Input product reference to update");

            Optional<TicketDetail> findTicketDetail = ticketDetails.stream().filter(p -> p.getRef().equalsIgnoreCase(reference)).findFirst();

            if (findTicketDetail.isPresent()) {
                Integer quantity = askForInt("Input new quantity");

                findTicketDetail.get().setQuantity(quantity);
                findTicketDetail.get().setAmount(quantity * findTicketDetail.get().getPrice());
                System.out.println(findTicketDetail);
            } else System.out.println("This product isn't in the ticket");
        } else System.out.println("There aren't products in ticket");
        return ticketDetails;

    }

    private List<TicketDetail> removeProductInTicketDetail(List<TicketDetail> ticketDetails) {
        System.out.println("Remove Product in TicketDetail ...\n");
        showObjectList(ticketDetails);

        if (ticketDetails.size() > 0) {
            String reference = askForString("Input product reference to remove");

            //TODO Verify if product exits in ticket detail
            Optional<TicketDetail> findTicketDetail = ticketDetails.stream().filter(p -> p.getRef().equalsIgnoreCase(reference)).findFirst();

            if (findTicketDetail.isPresent()) {

                Iterator<TicketDetail> it = ticketDetails.iterator();
                while (it.hasNext()) {
                    String ref = it.next().getRef();
                    if (ref.equalsIgnoreCase(reference)) {
                        it.remove();
                        System.out.println("Product " + reference + " remove in ticket");
                    }
                }
            } else System.out.println("There isn't reference in ticket");
        } else System.out.println("There aren't products in ticket");
        return ticketDetails;

    }

    private List<TicketDetail> addProductInTicketDetail(List<Product> products, List<TicketDetail> ticketDetails) {
        System.out.println("Add Product in Order ...\n");
        showObjectList(products);
        String reference = askForString("Input product reference");


        //TODO Verify if product exits
        Optional<Product> findProduct = products.stream().filter(p -> p.getRef().equalsIgnoreCase(reference)).findFirst();

        if (findProduct.isPresent()) {
            //TODO Verify if product exits in ticket detail
            Optional<TicketDetail> findTicketDetail = ticketDetails.stream().filter(p -> p.getRef().equalsIgnoreCase(reference)).findFirst();

            if (findTicketDetail.isPresent()) {
                System.out.println("Product already exists in the ticket");
            } else {
                Integer quantity = askForInt("Input quantity");
                TicketDetail newTicketDetail = new TicketDetail(findProduct.get().getId(), findProduct.get().getRef(),
                        quantity, findProduct.get().getPrice(), quantity * findProduct.get().getPrice());
                ticketDetails.add(newTicketDetail);

                System.out.println(newTicketDetail);
            }
        }
        return ticketDetails;

    }

    public void showObjectList(List<?> list) {
        list.stream().forEach(System.out::println);

    }

    private List<Product> loadProducts(ProductService productService) {
        return productService.getProducts();
    }

}
