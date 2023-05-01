package org.flowershop.utils;


import org.flowershop.utils.Scan.Scan;

public class Menu {

    /**
     *
     * @return integer with option menu
     */
    public static int showMainMenu() {
        String cad = "Select Option in FlowerShop...";
        cad += "\n1.Products";
        cad += "\n2.Tickets";
        cad += "\n3.Queries";
        cad += "\n4.Exit";
        int option = Scan.askForInt(cad);
        return option;
    }
    /**
     *
     * @return integer with option menu
     */
    public static int showTicketOptions() {
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

    /**
     *
     * @return integer with option menu
     */
    public static int showProductOptions() {
        String cad = "Select Option for Product...";
        cad += "\n1.Add Product";
        cad += "\n2.Update Product";
        cad += "\n3.Delete Product";
        cad += "\n4.Exit)";
        int option = Scan.askForInt(cad);
        return option;
    }
}
