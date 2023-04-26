package org.flowershop.repository;

import org.flowershop.domain.flowerShop.FlowerShop;

import java.util.List;

public interface IFlowerShopRepository {
    void addFlowerShop(FlowerShop flowerShop);
    List<FlowerShop> getAllFlowerShops();
    FlowerShop getFlowerShopById(Long id);
    FlowerShop getFlowerShopByRef(String ref);
    FlowerShop getFlowerShopByName(String name);
    void updateFlowerShop(FlowerShop flowerShop, String ref, String name);
    void removeFlowerShopById(long id);
    void removeFlowerShopByRef(String ref);
    void loadFlowerShops();
    void saveFlowerShops();
}
