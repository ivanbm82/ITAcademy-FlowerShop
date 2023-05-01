package org.flowershop.utils;

import org.flowershop.utils.Scan.Scan;

public class MenuTickets {
    /**
     *
     * @return integer with option menu
     */
    public static int showOption(String flowerShop) {
        System.out.println("----------------------------");
        System.out.println("   " + flowerShop);
        System.out.println("----------------------------");
        String cad = "Select Option in Order...";
        cad += "\n1.Add Product";
        cad += "\n2.Modify Quantity";
        cad += "\n3.Delete Product";
        cad += "\n4.Review Ticket";
        cad += "\n5.End Ticket and save";
        cad += "\n6.Exit (discard all changes)";
        int option = Scan.askForInt(cad);
        return option;
    }

}
