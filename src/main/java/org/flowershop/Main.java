package org.flowershop;


import org.flowershop.controller.FlowerShopController;

public class Main {

    public static void main(String[] args) {

        FlowerShopController flowerShopController = new FlowerShopController();

        flowerShopController.mainDataRequest();

        System.out.println("See you soon!!!");
    }
}