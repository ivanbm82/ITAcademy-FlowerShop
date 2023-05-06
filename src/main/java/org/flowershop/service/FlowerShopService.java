package org.flowershop.service;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.repository.IFlowerShopRepository;

import java.util.List;

public class FlowerShopService {
    private static FlowerShopService instance;
    private final IFlowerShopRepository repository;


    // This method gets the repository by injection dependencies.
    private FlowerShopService(IFlowerShopRepository repository) {
        this.repository = repository;
    }

    public static FlowerShopService getInstance(IFlowerShopRepository repository) {
        if (instance == null) {
            instance = new FlowerShopService(repository);
        }
        return instance;
    }

    // Methods
    public void addFlowerShop(FlowerShop flowerShop) {
        repository.addFlowerShop(flowerShop);
    }

    public List<FlowerShop> getFlowerShops() {
        return repository.getAllFlowerShops();
    }

}
