package org.flowershop.Controller;

import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;

import java.util.ArrayList;
import java.util.List;

public class TicketController {

    List<Product> products = new ArrayList<>();
    Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
    Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
    Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
    Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
    List<Product> products = Arrays.asList(product1, product2, product3, product4);


    public void ticketDataRequest() {
        System.out.println("Enter products order");


    }



}
