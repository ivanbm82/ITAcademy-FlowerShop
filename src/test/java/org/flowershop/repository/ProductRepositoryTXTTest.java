package org.flowershop.repository;

import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProductRepositoryTXTTest {
    private IProductRepository productRepository;


    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryTXT();
        productRepository.loadProducts();
    }

/*
    @AfterEach
    void close() throws IOException{
        // Finally delete temp file after the test
        if (file.exists()) {

        }
    }
*/

    @Test
    public void saveProducts() {
        // arrange
        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
        Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        // act
        products.forEach( productRepository::addProduct );

        // assert
        assertTrue(productRepository.getProducts().contains(product1));
        assertTrue(productRepository.getProducts().contains(product2));
        assertTrue(productRepository.getProducts().contains(product3));
        assertTrue(productRepository.getProducts().contains(product4));
    }

    @Test
    public void loadProducts() {
        List<Product> products = productRepository.getProducts();

        // The list has no products.
        assertEquals(0, products.size());

        // Adding file with some products.
        products = addData();

        // check after adding products to the list.
        assertEquals(4, products.size());
    }

    @DisplayName("Adding new Product")
    @Test
    public void addProduct() {
        // arrange
        Product product1 = new Tree("T032","Abeto", 23.60, 1.60f);

        // act
        productRepository.addProduct(product1);

        // assert
        assertEquals(product1.getId(), productRepository.getProducts().get(0).getId());
        assertEquals(product1.getRef(), productRepository.getProducts().get(0).getRef());
        assertEquals(product1.getName(), productRepository.getProducts().get(0).getName());
    }

    @DisplayName("Adding a group of products to txt file")
    @Test
    public void addSomeProducts() {
        // arrange
        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
        Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        // act
        products.forEach( productRepository::addProduct );

        // assert
        assertEquals(3, productRepository.getProducts().size());
        assertTrue(productRepository.getProducts().contains(product1));
        assertEquals(products.get(0).getRef(), productRepository.getProducts().get(0).getRef());

        assertTrue(productRepository.getProducts().contains(product1));
        assertEquals(products.get(1).getRef(), productRepository.getProducts().get(1).getRef());

        assertTrue(productRepository.getProducts().contains(product1));
        assertEquals(products.get(2).getRef(), productRepository.getProducts().get(2).getRef());

        assertTrue(productRepository.getProducts().contains(product1));
        assertEquals(products.get(3).getRef(), productRepository.getProducts().get(3).getRef());
    }

    @Test
    public void getProductById() {
        // arrange
        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
        Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        for(Product product: products) {
            productRepository.addProduct(product);
        }

        // act
        Product product = productRepository.getProductById(3);

        // assert
        assertEquals(product3.getId(), product.getId());
        assertEquals(product3.getRef(), product.getRef());
        assertTrue(productRepository.getProducts().contains(product3));
    }

    @Test
    public void getProductByRef() {
        // arrange
        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
        Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        products.forEach( productRepository::addProduct );

        // act
        Product product = productRepository.getProductByRef("T003");

        // assert
        assertEquals(product3.getId(), product.getId());
        assertEquals(product3.getRef(), product.getRef());
        assertTrue(productRepository.getProducts().contains(product3));
    }

    @DisplayName("Get products list")
    @Test
    public void getProducts() {
        // arrange
        List<Product> expectedProducts = new ArrayList<Product>();
        expectedProducts.add(new Tree("T001", "Abeto", 23.60, 1.60f));
        expectedProducts.add(new Decoration("T003", "Enredadera de pared", 20.95, "Madera"));
        expectedProducts.add(new Flower("T004", "Rosa", 23.60, "blanco"));

        expectedProducts.forEach( productRepository::addProduct );

        // act
        List<Product> products = productRepository.getProducts();

        // assert
        assertEquals(expectedProducts.get(0).getId(), products.get(0).getId());
        assertEquals(expectedProducts.get(0).getRef(), products.get(0).getRef());
        assertEquals(expectedProducts.get(0).getName(), products.get(0).getName());

        assertEquals(expectedProducts.get(1).getId(), products.get(1).getId());
        assertEquals(expectedProducts.get(1).getRef(), products.get(1).getRef());
        assertEquals(expectedProducts.get(1).getName(), products.get(1).getName());

        assertEquals(expectedProducts.get(2).getId(), products.get(2).getId());
        assertEquals(expectedProducts.get(2).getRef(), products.get(2).getRef());
        assertEquals(expectedProducts.get(2).getName(), products.get(2).getName());
    }

    @Test
    void updateProduct() {
        // arrange
        List<Product> products = addData();

        // act
        products.forEach( productRepository::addProduct );
        Product product = new Tree("T022", "Magnolia", 12.80, 0.30f);
        productRepository.updateProductById(2, product);

        // assert
        assertEquals(4, products.size());
        assertTrue(productRepository.getProducts().contains(product));
        // assertEquals(2, productRepository.getProducts().get(1).getId());
        assertEquals(product.getRef(), productRepository.getProducts().get(1).getRef());
        assertEquals(product.getName(), productRepository.getProducts().get(1).getName());
    }

    @Test
    void removeProductById() {
        // arrange
        List<Product> products = addData();

        // act
        products.forEach( productRepository::addProduct );
        productRepository.removeProductById(2);

        // assert
        assertEquals(3, productRepository.getProducts().size());
        assertTrue(productRepository.getProducts().contains(products.get(0)));
        assertFalse(productRepository.getProducts().contains(products.get(1)));
        assertTrue(productRepository.getProducts().contains(products.get(2)));
        assertTrue(productRepository.getProducts().contains(products.get(3)));
    }

    @Test
    void removeProductByRef() {
        // arrange
        List<Product> products = addData();

        // act
        products.forEach( productRepository::addProduct );
        productRepository.removeProductByRef("T002");

        // assert
        assertEquals(3, productRepository.getProducts().size());
        assertTrue(productRepository.getProducts().contains(products.get(0)));
        assertFalse(productRepository.getProducts().contains(products.get(1)));
        assertTrue(productRepository.getProducts().contains(products.get(2)));
        assertTrue(productRepository.getProducts().contains(products.get(3)));
    }

    List<Product> addData() {
        // arrange
        Product product1 = new Tree("T001", "Abeto", 23.60, 1.60f);
        Product product2 = new Tree("T002", "Magnolia", 16.80, 0.30f);
        Product product3 = new Decoration("T003", "Enredadera de pared", 20.95, "Madera");
        Product product4 = new Flower("T004", "Rosa", 23.60, "blanco");
        List<Product> products = Arrays.asList(product1, product2, product3, product4);
        return products;
    }

}
