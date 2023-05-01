package org.flowershop.controller;

import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;

import org.flowershop.repository.ProductRepositoryTXT;
import org.flowershop.service.ProductService;

import org.flowershop.exceptions.NegativeValueException;
import org.flowershop.utils.MenuProducts;
import org.flowershop.utils.Scan.Scan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductController {
    ProductService productService;
    MenuProducts menuProducts;
    DecimalFormat df;

    public ProductController() {
        productService = new ProductService( new ProductRepositoryTXT());
        menuProducts = new MenuProducts();
        df = new DecimalFormat("#.##");
    }


    public void stockHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuStock("Stock");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> showTotalStock();
                case 2 -> showStockByRef();
                case 3 -> addStockByRef();
                default -> System.out.println("Choose an option.");
            }
            Scan.askForString("Press enter to continue...");
            System.out.println();
        } while(!exit);
    }

    public void productHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuProducts("Products");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> treeHandleRequest();
                case 2 -> flowerHandleRequest();
                case 3 -> decorationHandleRequest();
                default -> System.out.println("Choose an option:");
            }
            System.out.println();
        } while(!exit);

    }

    public void treeHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuCRUD("Trees management");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> addTree();
                case 2 -> updateTree();
                case 3 -> remove();
                default -> System.out.println("Choose an option:");
            }
            System.out.println();
        } while(!exit);

    }

    public void flowerHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuCRUD("Flowers management");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> addFlower();
                case 2 -> updateFlower();
                case 3 -> remove();
                default -> System.out.println("Choose an option:");
            }
            System.out.println();
        } while(!exit);

    }

    public void decorationHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuCRUD("Decoration management");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> addDecoration();
                case 2 -> updateDecoraton();
                case 3 -> remove();
                default -> System.out.println("Choose an option:");
            }
            System.out.println();
        } while(!exit);

    }


    public void remove() {
        String ref = Scan.askForString("Delete product by reference:");
        productService.removeProductByRef(ref);
    }

    public void addTree() {
        System.out.println("add new product");
        String ref = Scan.askForString("Ref:");
        String name = Scan.askForString("Name:");
        float height = Scan.askForFloat("Height:");
        double price = Scan.askForDouble("Price:");
        Tree tree = new Tree(ref, name, price, height);
        int stock = Scan.askForInt("Stock:");
        try {
            tree.setStock(stock);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
        productService.addProduct(tree);
    }

    public void updateTree() {
        String ref = Scan.askForString("Select product by ref:");
        Product product = productService.getProductByRef(ref);
        if ( !(product instanceof Tree) ) {
            System.out.println("unrecognized tree product");
            return;
        }
        Tree tree = (Tree) product;
        String name = Scan.askForString("Name:");
        float height = Scan.askForFloat("Height:");
        double price = Scan.askForDouble("Price:");
        tree.setName(name);
        tree.setHeight(height);
        try {
            tree.setPrice(price);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFlower() {
        System.out.println("add new product");
        String ref = Scan.askForString("Ref:");
        String name = Scan.askForString("Name:");
        String color = Scan.askForString("Color:");
        double price = Scan.askForDouble("Price:");
        Flower flower = new Flower(ref, name, price, color);
        int stock = Scan.askForInt("Stock:");
        try {
            flower.setStock(stock);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
        productService.addProduct(flower);
    }

    public void updateFlower() {
        String ref = Scan.askForString("Select product by ref:");
        Product product = productService.getProductByRef(ref);
        if ( !(product instanceof Flower) ) {
            System.out.println("unrecognized tree product");
            return;
        }
        Flower flower = (Flower) product;
        String name = Scan.askForString("Name:");
        String color = Scan.askForString("Color:");
        double price = Scan.askForDouble("Price:");
        flower.setName(name);
        flower.setColor(color);
        try {
            flower.setPrice(price);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDecoration() {
        System.out.println("add new product");
        String ref = Scan.askForString("Ref:");
        String name = Scan.askForString("Name:");
        String type = Scan.askForString("Type:");
        double price = Scan.askForDouble("Price:");
        Decoration decoration = new Decoration(ref, name, price, type);
        int stock = Scan.askForInt("Stock:");
        try {
            decoration.setStock(stock);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
        productService.addProduct(decoration);
    }

    public void updateDecoraton() {
        String ref = Scan.askForString("Select product by ref:");
        Product product = productService.getProductByRef(ref);
        if ( !(product instanceof Decoration) ) {
            System.out.println("unrecognized tree product");
            return;
        }
        Decoration decoration = (Decoration) product;
        String name = Scan.askForString("Name:");
        String type = Scan.askForString("Type:");
        double price = Scan.askForDouble("Price:");
        decoration.setName(name);
        decoration.setType(type);
        try {
            decoration.setPrice(price);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStockByRef() {
        String ref = Scan.askForString("Ref:");
        int quantity = Scan.askForInt(("Quantity:"));
        productService.updateStockbyRef(ref, quantity);
    }

    public double getTotalStoreValue() {
        List<Product> products = productService.getProducts();
        double totalValue = products.stream()
                .mapToDouble(product -> product.getPrice() * product.getStock())
                .sum();
        return totalValue;
    }

    public void showTotalStock() {
        List<Product> products = productService.getProducts();
        List<Tree> trees = new ArrayList<>();
        List<Flower> flowers = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();

        products.forEach( product -> {
            if (product instanceof Tree) {
                trees.add((Tree) product);
            } else if (product instanceof Flower) {
                flowers.add((Flower) product);
            }else if (product instanceof Decoration) {
                decorations.add((Decoration) product);
            }
        } );
        // Display terminal
        System.out.println("TREES");
        trees.forEach(tree -> System.out.println("    " + tree.getRef() + ", " + tree.getName()
                + ", height: " + tree.getHeight()
                + ", price: " + df.format(tree.getPrice())
                + ", stock: " + tree.getStock()));
        System.out.println("FLOWERS");
        flowers.forEach(flower -> System.out.println("    " + flower.getRef() + ", " + flower.getName()
                + ", color: " + flower.getColor()
                + ", price: " + df.format(flower.getPrice())
                + ", stock: " + flower.getStock()));
        System.out.println("DECORATION");
        decorations.forEach(decoration -> System.out.println("    " + decoration.getRef() + ", " + decoration.getName()
                + ", tipo: " + decoration.getType()
                + ", price: " + df.format(decoration.getPrice())
                + ", stock: " + decoration.getStock()));
    }

    public void showStockByRef() {
        String ref = Scan.askForString("Ref: ");
        Product product = productService.getProductByRef(ref);

        if (product instanceof Tree) {
            System.out.print("TREE: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", height: " + ((Tree) product).getHeight()
                    + ", price: " + df.format(product.getPrice())
                    + ", stock: " + product.getStock());
        } else if (product instanceof Flower) {
            System.out.print("FLOWER: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", color: " + ((Flower) product).getColor()
                    + ", price: " + df.format(product.getPrice())
                    + ", stock: " + product.getStock());
        }else if (product instanceof Decoration) {
            System.out.print("DECORATION: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", type: " + ((Decoration) product).getType()
                    + ", price: " + df.format(product.getPrice())
                    + ", stock: " + product.getStock());
        } else {
            System.out.println("There is no product with this reference.");
        }

    }


    private List<Product> loadProducts() {

        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Tree("T003", "Pino", 23.60, 1.60f);
        Product product4 = new Tree("T004", "Limonero", 16.80, 0.30f);
        Product product5 = new Tree("T005", "Almendro", 23.60, 1.60f);
        Product product6 = new Tree("T006", "Manzano", 16.80, 0.30f);

        Product product7 = new Flower("F001", "Rosa", 23.60, "blanco");
        Product product8 = new Flower("F002", "Rosa", 23.60, "rojo");
        Product product9 = new Flower("F003", "Rosa", 23.60, "rosa");
        Product product10 = new Flower("F004", "Margarita", 23.60, "blanca/amarilla");
        Product product11 = new Flower("F005", "Lirio", 23.60, "blanca");
        Product product12 = new Flower("F006", "Lirio", 23.60, "amarillo");
        Product product13 = new Flower("F007", "Amapola", 23.60, "roja");
        Product product14 = new Flower("F008", "Amapola", 23.60, "amarilla");

        Product product15 = new Decoration("D001", "Enredadera de pared", 20.95, "Madera");
        Product product16 = new Decoration("D002", "Centro gris", 2.95, "Madera");
        Product product17 = new Decoration("D003", "Adorno flor", 03.95, "Madera");
        Product product18 = new Decoration("D004", "Palo", 2.0, "Plastico");
        Product product19 = new Decoration("D005", "Vaso flor", 4.0, "Madera");
        Product product20 = new Decoration("D006", "Macetero", 7.0, "Plastico");

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10,
                product11, product12, product13, product14, product15, product16, product17, product18, product19, product20);

        return products;
    }

}
