package org.flowershop.service;

import org.flowershop.utils.UtilsQueryTicket;
import org.flowershop.domain.products.Product;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QueryService {
    public int checkStock(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getStock)
                .sum();
    }
    public double checkValueStock(List<Product> products) {
        double totalPrice;
        return totalPrice = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();
    }

    public List<UtilsQueryTicket> getSalesByDateRange(List<UtilsQueryTicket> utilsQueryTickets) {

        LocalDate[] dates = getStartDateAndEndDate();
        LocalDate startDate = dates[0];
        LocalDate endDate = dates[1];

        return utilsQueryTickets.stream()
                .filter(utilsQueryTicket -> utilsQueryTicket.getDate().isAfter(startDate) && utilsQueryTicket.getDate().isBefore(endDate))
                .collect(Collectors.toList());
    }
    public double getTotalProfitByDateRange(List<UtilsQueryTicket> utilsQueryTickets) {

        LocalDate[] dates = getStartDateAndEndDate();
        LocalDate startDate = dates[0];
        LocalDate endDate = dates[1];

        return getSalesByDateRange(utilsQueryTickets).stream()
                .mapToDouble(UtilsQueryTicket::getTotalPrice)
                .sum();
    }
    public static LocalDate[] getStartDateAndEndDate() {
        LocalDate startDate = null;
        LocalDate endDate = null;
        Scanner scanner = new Scanner(System.in);

        while (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            System.out.println("Ingrese la fecha de inicio en formato yyyy-mm-dd:");
            try {
                startDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }

            System.out.println("Ingrese la fecha de fin en formato yyyy-mm-dd:");
            try {
                endDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }

            if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                System.out.println("La fecha de inicio debe ser anterior a la fecha de fin. Intente nuevamente.");
            }
        }

        return new LocalDate[] { startDate, endDate };
    }


}