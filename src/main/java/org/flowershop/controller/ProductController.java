package org.flowershop.controller;

import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;
import org.flowershop.exceptions.NegativeValueException;
import org.flowershop.repository.ProductRepositoryTXT;
import org.flowershop.service.ProductService;
import org.flowershop.utils.MenuProducts;
import org.flowershop.utils.Scan.Scan;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    ProductService productService;
    MenuProducts menuProducts;


    public ProductController() {
        productService = new ProductService( new ProductRepositoryTXT());
        menuProducts = new MenuProducts();
    }


    public void stockHandleRequest() {
        boolean exit = false;
        int option;

        do {
            option = menuProducts.menuStock("Stock");
            switch (option) {
                case 0 -> exit = true;
                case 1 -> showTotalStockt();
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

    public void showTotalStockt() {
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
                + ", price: " + tree.getPrice()
                + ", stock: " + tree.getStock()));
        System.out.println("FLOWERS");
        flowers.forEach(flower -> System.out.println("    " + flower.getRef() + ", " + flower.getName()
                + ", color: " + flower.getColor()
                + ", price: " + flower.getPrice()
                + ", stock: " + flower.getStock()));
        System.out.println("DECORATION");
        decorations.forEach(decoration -> System.out.println("    " + decoration.getRef() + ", " + decoration.getName()
                + ", tipo: " + decoration.getType()
                + ", price: " + decoration.getPrice()
                + ", stock: " + decoration.getStock()));
    }

    public void showStockByRef() {
        String ref = Scan.askForString("Ref: ");
        Product product = productService.getProductByRef(ref);

        if (product instanceof Tree) {
            System.out.print("TREE: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", height: " + ((Tree) product).getHeight()
                    + ", price: " + product.getPrice()
                    + ", stock: " + product.getStock());
        } else if (product instanceof Flower) {
            System.out.print("FLOWER: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", color: " + ((Flower) product).getColor()
                    + ", price: " + product.getPrice()
                    + ", stock: " + product.getStock());
        }else if (product instanceof Decoration) {
            System.out.print("DECORATION: ");
            System.out.println(product.getRef() + ", " + product.getName()
                    + ", type: " + ((Decoration) product).getType()
                    + ", price: " + product.getPrice()
                    + ", stock: " + product.getStock());
        } else {
            System.out.println("There is no product with this reference.");
        }
    }

}
