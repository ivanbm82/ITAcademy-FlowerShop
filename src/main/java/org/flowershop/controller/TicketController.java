package org.flowershop.controller;

import org.flowershop.domain.products.Product;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.domain.tickets.TicketDetail;
import org.flowershop.repository.IProductRepository;
import org.flowershop.repository.ITicketRepository;
import org.flowershop.service.ProductService;
import org.flowershop.service.TicketService;
import org.flowershop.utils.menu.MenuTickets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static org.flowershop.utils.Scan.Scan.askForInt;
import static org.flowershop.utils.Scan.Scan.askForString;


public class TicketController {
    private static TicketController instance;
    ProductService productService;
    TicketService ticketService;


    private TicketController(IProductRepository productRepository, ITicketRepository ticketRepository) {
        productService = ProductService.getInstance(productRepository);
        ticketService = TicketService.getInstance(ticketRepository);
    }


    public static TicketController getInstance(IProductRepository productRepository, ITicketRepository ticketRepository) {
        if (instance == null) {
            instance = new TicketController(productRepository, ticketRepository);
        }
        return instance;
    }


    public void ticketDataRequest() {
        List<Product> products = productService.getProducts();
        List<TicketDetail> ticketDetails = new ArrayList<>();

        boolean exit = false;
        int option;

        do {
            option = MenuTickets.showOption("Tickets");
            switch (option) {
                case 0 -> {ticketDetails.clear(); exit = true;}
                case 1 -> addProductInTicketDetail(products, ticketDetails);
                case 2 -> updateQuantityInTicketDetail(ticketDetails);
                case 3 -> removeProductInTicketDetail(ticketDetails);
                case 4 -> showProductsInTicket(ticketDetails);
                case 5 -> saveTicket(ticketService, ticketDetails);
                default -> System.out.println("Choose an option.");
            }
            System.out.println();
        } while(!exit);

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

    private void saveTicket(TicketService ticketService, List<TicketDetail> ticketDetails) {
        Double total = ticketDetails.stream().mapToDouble(TicketDetail::getAmount).sum();
        Ticket ticket = new Ticket(ticketService.getLastTicketId(), new Date(), 1L, total, true);

        ticketDetails.stream().forEach(ticket::addTicketDetail);
        ticketDetails.stream().forEach(td -> {
                                                ticket.addTicketDetail(td);
                                                productService.updateStockbyRef(td.getRef(), td.getQuantity() * -1);
                                            });

        try {
            ticketService.addTicket(ticket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ticket);

        ticketDetails.clear();
    }

    private List<TicketDetail> updateQuantityInTicketDetail(List<TicketDetail> ticketDetails) {

        if (ticketDetails.size() > 0) {
            System.out.println("Update Product in TicketDetail ...\n");
            showObjectList(ticketDetails);

            String reference = askForString("Input product reference to update");

            Optional<TicketDetail> findTicketDetail = ticketDetails.stream().filter(p -> p.getRef().equalsIgnoreCase(reference)).findFirst();

            if (findTicketDetail.isPresent()) {
                Integer quantity = askForInt("Input new quantity");

                Product findProduct = productService.getProductByRef(findTicketDetail.get().getRef());

                if (findProduct.getStock() >= quantity) {

                    findTicketDetail.get().setQuantity(quantity);
                    findTicketDetail.get().setAmount(quantity * findTicketDetail.get().getPrice());
                    System.out.println(findTicketDetail);

                } else System.out.println("Available stock: " + findProduct.getStock());



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

    private List<TicketDetail> addProductInTicketDetail
            (List<Product> products, List<TicketDetail> ticketDetails) {
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
                //TODO Check if there are enough stock
                if (findProduct.get().getStock() >= quantity) {
                    TicketDetail newTicketDetail = new TicketDetail(findProduct.get().getId(), findProduct.get().getRef(),
                            quantity, findProduct.get().getPrice(), quantity * findProduct.get().getPrice());
                    ticketDetails.add(newTicketDetail);

                    System.out.println(newTicketDetail);
                } else System.out.println("Available stock: " + findProduct.get().getStock());
            }
        }
        return ticketDetails;

    }

    public void showObjectList(List<?> list) {
        list.forEach(System.out::println);

    }

}
