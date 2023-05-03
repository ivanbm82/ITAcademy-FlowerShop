package org.flowershop;

import org.flowershop.controller.FlowerShopController;
import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.utils.Scan.Scan;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FlowerShopController flowerShopController = new FlowerShopController();
        List<FlowerShop> flowerShops = flowerShopController.load();
        String flowerShopName;

        if ( flowerShops.isEmpty() ) {
            flowerShopName = Scan.askForString("Enter the name of the florist: ");
            if (flowerShopName != "" || flowerShopName != null) {
                FlowerShop flowerShop = new FlowerShop(flowerShopName);
                flowerShopController.add(flowerShop);
                flowerShopController.flowerShopHandleRequest();
            }
        } else {
            flowerShopController.flowerShopHandleRequest();
        }
        System.out.println("See you soon!!!");
    }

}