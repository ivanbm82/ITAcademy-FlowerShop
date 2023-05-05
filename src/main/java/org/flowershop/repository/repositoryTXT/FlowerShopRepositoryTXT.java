package org.flowershop.repository.repositoryTXT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.repository.IFlowerShopRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class FlowerShopRepositoryTXT implements IFlowerShopRepository {
    private static FlowerShopRepositoryTXT instance;
    private Properties properties;
    private String fileName;
    File file;
    private ObjectMapper objectMapper;
    private List<FlowerShop> flowerShops;


    private FlowerShopRepositoryTXT() {
        properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileName = properties.getProperty("fileFlowershop");

        objectMapper = new ObjectMapper();
        file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // Create directory if not exists
            try {
                file.createNewFile(); // Create the file if not exists.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        loadFlowerShops();
    }


    public static FlowerShopRepositoryTXT getInstance() {
        if (instance == null) {
            instance = new FlowerShopRepositoryTXT();
        }
        return instance;
    }


    /**
     * Add new flower shop.
     *
     * @param flowerShop  The flower shop to save
     */
    @Override
    public void addFlowerShop(FlowerShop flowerShop) {
        if (!flowerShops.stream().anyMatch(p -> p.getName().equals(flowerShop.getName()))) {
            flowerShops.add(flowerShop);
            saveFlowerShops();
            System.out.println("The flower shop has been added.");
        } else {
            System.out.println("The flower shop with name " + flowerShop.getName() + " already exists.");
        }
    }

    /**
     * This method returns the list of flower shops.
     *
     * @return  The list of flower shops.
     */
    @Override
    public List<FlowerShop> getAllFlowerShops() {
        return flowerShops;
    }

    /**
     * This method get a flower shop by id
     *
     * @param id
     * @return    The flower shop with the specified id
     */
    @Override
    public FlowerShop getFlowerShopById(Long id) {
        for (FlowerShop flowerShop: flowerShops) {
            if (flowerShop.getId() == id) {
                return flowerShop;
            }
        }
        return null;
    }

    /**
     * This method get a flower shop by name.
     *
     * @param name  The name of the flower shop.
     * @return      The flower shop with the specified name.
     */
    @Override
    public FlowerShop getFlowerShopByName(String name) {
        for (FlowerShop flowerShop: flowerShops) {
            if (flowerShop.getName().equals(name)) {
                return flowerShop;
            }
        }
        return null;
    }

    /**
     * This method updates the flower shop passed by parameter. The method takes the value of the
     * flower shop id and updates it.
     *
     * @param flowerShop  The flower shop with updated values.
     */
    @Override
    public void updateFlowerShop(FlowerShop flowerShop, String name) {
        if (flowerShop == null) return;

        flowerShop.setName(name);
        saveFlowerShops();
    }

    /**
     * This method removes from de list the specified flower shop by id.
     *
     * @param id
     */
    @Override
    public void removeFlowerShopById(long id) {
        Iterator<FlowerShop> iterator = flowerShops.iterator();
        while (iterator.hasNext()) {
            FlowerShop flowerShop = iterator.next();
            if (flowerShop.getId() == id) {
                iterator.remove();
                break;
            }
        }
        saveFlowerShops();
    }

    /**
     * This method saves the list of flower shops to a file.
     */
    @Override
    public void saveFlowerShops() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (FlowerShop flowerShop : flowerShops) {
                String json = objectMapper.writeValueAsString(flowerShop);
                writer.write(json);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Exception to save flower shop to file." + e.getMessage());
        }
    }

    /**
     * This method loads the flower shops file.
     */
    @Override
    public void loadFlowerShops(){
        //if (!file.exists()) return;

        String line;
        FlowerShop flowerShop = null;
        List<FlowerShop> flowerShops = new ArrayList<FlowerShop>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                flowerShop = objectMapper.readValue(line, FlowerShop.class);
                flowerShops.add(flowerShop);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.flowerShops = flowerShops;
    }

}
