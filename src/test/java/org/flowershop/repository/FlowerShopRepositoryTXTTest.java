package org.flowershop.repository;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.repository.repositoryTXT.FlowerShopRepositoryTXT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlowerShopRepositoryTXTTest {

    private IFlowerShopRepository repository;

    @BeforeEach
    public void setUp() {
        repository = FlowerShopRepositoryTXT.getInstance();
        repository.loadFlowerShops();
    }

    @Test
    public void saveFlowerShops() {
        // arrange
        FlowerShop shop1 = new FlowerShop("MyFlowerShop");
        FlowerShop shop2 = new FlowerShop("MyFlowerShop2");
        List<FlowerShop> flowerShops = Arrays.asList(shop1, shop2);
        // act
        flowerShops.forEach( repository::addFlowerShop );
        // assert
        assertTrue(repository.getAllFlowerShops().contains(shop1));
        assertTrue(repository.getAllFlowerShops().contains(shop2));
    }

    @Test
    public void loadFlowerShops() {
        List<FlowerShop> shops = repository.getAllFlowerShops();
        // The list have two flower shops by the previous test
        assertEquals(2, shops.size());
    }

    @Test
    public void addFlowerShop() {
        // arrange
        FlowerShop shop = new FlowerShop("MyFlorist3");
        // act
        repository.addFlowerShop(shop);
        // assert
        assertTrue(repository.getAllFlowerShops().contains(shop));
    }

    @Test
    public void getFlowerShopByName() {
        repository.getFlowerShopByName("MyFlorist3");
        assertEquals("MyFlorist3", repository.getFlowerShopByName("MyFlorist3").getName());
    }

    @Test
    public void getAllFlowerShops() {
        repository.getAllFlowerShops();
        assertEquals(3, repository.getAllFlowerShops().size());
    }


}
