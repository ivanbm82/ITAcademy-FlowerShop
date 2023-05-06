package org.flowershop.utils.menu;

import org.flowershop.utils.Scan.Scan;


public class MenuFlowerShop {

    public int menu(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        System.out.println("  1. Check stock.");
        System.out.println("  2. Manage products.");
        System.out.println("  3. Manage tickets.");
        System.out.println("  4. Show finance.");
        System.out.println("  0. Exit.");

        return Scan.askForInt("Choose an option: ");
    }

    public int menuFinancial(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        System.out.println("  1. Total value of the flower shop.");
        System.out.println("  2. Total profits.");
        System.out.println("  3. List of sales tickets.");
        System.out.println("  0. Back.");

        return Scan.askForInt("Choose an option: ");
    }


}
