package org.flowershop.repository;

import org.flowershop.domain.flowerShop.FlowerShop;

import java.util.List;

public interface IFlowerShopRepository {
    void addFlowerShop(FlowerShop flowerShop);
    List<FlowerShop> getAllFlowerShops();

}
