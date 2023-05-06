package org.flowershop;

import org.bson.Document;
import org.flowershop.controller.FlowerShopController;
import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.repository.repositoryMongoDB.MongoDbRepository;
import org.flowershop.utils.Scan.Scan;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /* ---------------------------
         * Choose type of persistence
         * --------------------------- */
        int typePersistence;
        do{
            System.out.println("Type of persistence");
            System.out.println("    1.- txt");
            System.out.println("    2.- mySql");
            System.out.println("    3.- mongodb");
            typePersistence = Scan.askForInt("Choose an option:");
        } while (typePersistence < 1 || typePersistence > 3);


        /* -------------------------
         * start of the application
         * ------------------------- */
        FlowerShopController flowerShopController = new FlowerShopController(typePersistence);
        List<FlowerShop> flowerShops = flowerShopController.load();
        String flowerShopName;

        if ( flowerShops.isEmpty() ) {
            flowerShopName = Scan.askForString("Enter the name of the florist: ").trim();
            if (!flowerShopName.equals("") ) {
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