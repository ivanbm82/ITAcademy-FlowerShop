package org.flowershop.utils.menu;

import org.flowershop.utils.Scan.Scan;

public class MenuProducts {
    public int menuStock(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        System.out.println("  1. Show total stock.");
        System.out.println("  2. Show stock of products by ref.");
        System.out.println("  3. Add stock by ref.");
        System.out.println("  0. Back.");

        return Scan.askForInt("Choose an option: ");
    }

    public int menuProducts(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        System.out.println("Product management:");
        System.out.println("  1. Trees");
        System.out.println("  2. Flowers");
        System.out.println("  3. Decorations");
        System.out.println("  0. Back");

        return Scan.askForInt("Choose an option:");
    }


    public int menuCRUD(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        System.out.println("CRUD management:");
        System.out.println("  1. Add");
        System.out.println("  2. Update");
        System.out.println("  3. Remove");
        System.out.println("  0. Back");

        return Scan.askForInt("Choose an option:");
    }



}
