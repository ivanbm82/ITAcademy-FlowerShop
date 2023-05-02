package org.flowershop.controller;

import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;

import org.flowershop.repository.ProductRepositorySQL;
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
        /*
        productService = new ProductService(
                new ProductRepositorySQL("jdbc:mysql://root:Ozs9AVywm8d9r6mAI2lo@containers-us-west-131.railway.app:6126/railway","root","Ozs9AVywm8d9r6mAI2lo")
        );
         */
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
        String ref = Scan.askForString("Delete product by reference:").toUpperCase().trim();
        productService.removeProductByRef(ref);
        System.out.println("The product with ref:" + ref + " has been deleted.");
    }

    public void addTree() {
        System.out.println("Add new product");
        String ref = Scan.askForString("Ref:").toUpperCase().trim();
        if ( productService.getProductByRef(ref) != null ) {
            System.out.println("The product with ref " + ref + " already exists.");
        } else {
            String name = Scan.askForString("Name:").toUpperCase().trim();
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
            System.out.println("The product " + ref + " " + name + " has been added.");
        }
    }

    public void updateTree() {
        String ref = Scan.askForString("Select product by ref:").toUpperCase().trim();
        Product product = productService.getProductByRef(ref);
        if (product == null) {
            System.out.println("The product with ref " + ref + " is not found.");
        } else {
            if ( !(product instanceof Tree) ) {
                System.out.println("unrecognized tree product");
                return;
            }
            Tree tree = (Tree) product;
            String name = Scan.askForString("Name:").toUpperCase().trim();
            float height = Scan.askForFloat("Height:");
            double price = Scan.askForDouble("Price:");
            try {
                tree.setName(name);
                tree.setHeight(height);
                tree.setPrice(price);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addFlower() {
        System.out.println("Add new product");
        String ref = Scan.askForString("Ref:").toUpperCase().trim();
        if ( productService.getProductByRef(ref) != null ) {
            System.out.println("The product with ref " + ref + " already exists.");
        } else {
            String name = Scan.askForString("Name:").toUpperCase().trim();
            String color = Scan.askForString("Color:").toUpperCase().trim();
            double price = Scan.askForDouble("Price:");
            Flower flower = new Flower(ref, name, price, color);
            int stock = Scan.askForInt("Stock:");
            try {
                flower.setStock(stock);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
            productService.addProduct(flower);
            System.out.println("The product " + ref + " " + name + " has been added.");
        }
    }

    public void updateFlower() {
        String ref = Scan.askForString("Select product by ref:").toUpperCase().trim();
        Product product = productService.getProductByRef(ref);
        if (product == null) {
            System.out.println("The product with ref " + ref + " is not found.");
        } else {
            if ( !(product instanceof Flower) ) {
                System.out.println("unrecognized tree product");
                return;
            }
            Flower flower = (Flower) product;
            String name = Scan.askForString("Name:").toUpperCase().trim();
            String color = Scan.askForString("Color:").toUpperCase().trim();
            double price = Scan.askForDouble("Price:");
            try {
                flower.setName(name);
                flower.setColor(color);
                flower.setPrice(price);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
            System.out.println("The product " + ref + " " + name + " has been updated.");
        }
    }

    public void addDecoration() {
        System.out.println("Add new product");
        String ref = Scan.askForString("Ref:").toUpperCase().trim();
        if ( productService.getProductByRef(ref) != null ) {
            System.out.println("The product with ref " + ref + " already exists.");
        } else {
            String name = Scan.askForString("Name:").toUpperCase().trim();
            String type = Scan.askForString("Type:").toUpperCase().trim();
            double price = Scan.askForDouble("Price:");
            Decoration decoration = new Decoration(ref, name, price, type);
            int stock = Scan.askForInt("Stock:");
            try {
                decoration.setStock(stock);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
            productService.addProduct(decoration);
            System.out.println("The product " + ref + " " + name + " has been added.");
        }
    }

    public void updateDecoraton() {
        String ref = Scan.askForString("Select product by ref:");
        Product product = productService.getProductByRef(ref);
        if (product == null) {
            System.out.println("The product with ref " + ref + " is not found.");
        } else {
            if ( !(product instanceof Decoration) ) {
                System.out.println("unrecognized tree product");
                return;
            }
            Decoration decoration = (Decoration) product;
            String name = Scan.askForString("Name:");
            String type = Scan.askForString("Type:");
            double price = Scan.askForDouble("Price:");
            try {
                decoration.setName(name);
                decoration.setType(type);
                decoration.setPrice(price);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
            System.out.println("The product " + ref + " " + name + " has been updated.");
        }
    }


    public void addStockByRef() {
        if ( getTotalStock() <= 0 ) {
            System.out.println("No stock available.");
        } else {
            String ref = Scan.askForString("Ref:").toUpperCase().trim();
            if (productService.getProductByRef(ref) != null) {
                int quantity = Scan.askForInt(("Quantity:"));
                productService.updateStockbyRef(ref, quantity);
                System.out.println(productService.getProductByRef(ref).toString());
            } else {
                System.out.println("The product with ref " + ref + " does not exist.");
            }
        }
    }

    public double getTotalStoreValue() {
        List<Product> products = productService.getProducts();
        double totalValue = products.stream()
                .mapToDouble(product -> product.getPrice() * product.getStock())
                .sum();
        return totalValue;
    }

    public int getTotalStock() {
        List<Product> products = productService.getProducts();
        return products.stream().mapToInt(Product::getStock).sum();
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
        if (getTotalStock() <= 0) {
            System.out.println("No stock available.");
            return;
        }
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

}
