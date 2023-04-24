package org.flowershop.utils.Menu;

import org.flowershop.utils.Scan.Scan;

public class MenuTickets {

    public static int showOption() {
        String cad = "Select Option in Order...";
        cad += "\n1.Add Product";
        cad += "\n2.Modify Quantity";
        cad += "\n3.Delete Product";
        cad += "\n4.Review Ticket";
        cad += "\n5.End Ticket";
        cad += "\n6.Exit (discard all changes)";
        int option = Scan.askForInt(cad);
        return option;
    }

}
