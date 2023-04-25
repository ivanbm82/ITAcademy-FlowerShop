package org.flowershop.service;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.repository.IFlowerShopRepository;

import java.util.List;

public class FlowerShopService {
    private IFlowerShopRepository repository;


    // This method gets the repository by injection dependencies.
    public FlowerShopService(IFlowerShopRepository repository) {
        this.repository = repository;
    }


    // Methods
    public void addFlowerShop(FlowerShop flowerShop) {
        repository.addFlowerShop(flowerShop);
    }

    public List<FlowerShop> getFlowerShops() {
        return repository.getAllFlowerShops();
    }

    public FlowerShop getFlowerShopById(long id) {
        return repository.getFlowerShopById(id);
    }

    public FlowerShop getFlowerShopByRef(String ref) {
        return repository.getFlowerShopByRef(ref);
    }

    void updateFlowerShop(FlowerShop flowerShop, String ref, String name) {
        repository.updateFlowerShop(flowerShop, ref, name);
    }

    public void removeFlowerShopById(long id) {
        repository.removeFlowerShopById(id);
    }

    public void removeFlowerShopByRef(String ref) {
        repository.getFlowerShopByRef(ref);
    }

}
