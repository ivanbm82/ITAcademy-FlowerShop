package org.flowershop.repository.repositorySQL;

import org.flowershop.controller.FlowerShopController;
import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.repository.IFlowerShopRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlowerShopRepositorySQL implements IFlowerShopRepository {
    private final Properties properties;
    private String uri;
    private String user;
    private String password;


    public FlowerShopRepositorySQL() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uri = properties.getProperty("uri_sql");
        user = properties.getProperty("user");
        password = properties.getProperty("pass");
    }

    @Override
    public void addFlowerShop(FlowerShop flowerShop) {
        try {
            Connection connection = DriverManager.getConnection(uri, user, password);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO flowershop (name) VALUES (?)");
            statement.setString(1, flowerShop.getName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Flower shop added successfully.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error adding flower shop: " + e.getMessage());
        }
    }

    @Override
    public List<FlowerShop> getAllFlowerShops() {
        List<FlowerShop> flowerShops = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(uri, user, password);
            String query = "SELECT * FROM flowershop";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FlowerShop flowerShop = new FlowerShop();
                flowerShop.setId(resultSet.getLong("id"));
                flowerShop.setName(resultSet.getString("name"));

                flowerShops.add(flowerShop);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error getting flower shops: " + e.getMessage());
        }
        return flowerShops;
    }

    @Override
    public FlowerShop getFlowerShopById(Long id) {
        return null;
    }

    @Override
    public FlowerShop getFlowerShopByName(String name) {
        return null;
    }

    @Override
    public void updateFlowerShop(FlowerShop flowerShop, String name) {

    }

    @Override
    public void removeFlowerShopById(long id) {

    }

    @Override
    public void loadFlowerShops() {

    }

    @Override
    public void saveFlowerShops() {

    }
}
