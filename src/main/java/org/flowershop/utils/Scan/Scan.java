package org.flowershop.utils.Scan;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    /**
     *
     * @param message Message to send to the screen
     * @return. Return a int entered on screen
     */
    public static int askForInt(String message) {
        Scanner sc = new Scanner(System.in);
        int integer  = 0;
        boolean ok = false;
        do {
            System.out.println(message);
            try {
                integer = sc.nextInt();
                ok = true;
            } catch (InputMismatchException ex) {
                System.out.println("ERROR. Input type mismatch\n");
            } finally {
                sc.nextLine();
            }

        } while (!ok);
        return integer;
    }

    /**
     *
     * @param message Message to send to the screen
     * @return. Returns a double entered by terminal
     */
    public static double askForDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        double value = 0;
        boolean exit = false;

        System.out.println(message);
        do {
            try {
                value = scanner.nextDouble();

                exit = true;
            } catch (InputMismatchException ime) {
                System.out.println(message + " (format error, use comma)");
            } finally {
                scanner.nextLine();
            }
        } while (!exit);

        return value;
    }

    /**
     *
     * @param message Message to send to the screen
     * @return. Returns a float entered by terminal
     */
    public static float askForFloat(String message) {
        Scanner scanner = new Scanner(System.in);
        float value = 0;
        boolean exit = false;

        System.out.println(message);
        do {
            try {
                value = scanner.nextFloat();
                exit = true;
            } catch (InputMismatchException ime) {
                System.out.println(message + " (format error, use comma)");
            } finally {
                scanner.nextLine();
            }
        } while (!exit);

        return value;
    }


    /**
     *
     * @param message Message to send to the screen
     * @return. Return a string entered on screen
     */
    public static String askForString(String message) {

        Scanner sc = new Scanner(System.in);
        String cadena  = "";
        boolean ok = false;
        do {
            System.out.println(message);
            try {
                cadena = sc.nextLine();
                ok = true;
            } catch (InputMismatchException ex) {
                System.out.println("ERROR. Input type mismatch\n");
            }

        } while (!ok);
        return cadena;
    }
}
