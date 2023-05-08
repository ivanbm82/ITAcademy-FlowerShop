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

        createTables();
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


    public void createTables() {
        System.out.println("Create database if it doesn't exist.");
        try (Connection conn = DriverManager.getConnection(this.uri, this.user, this.password);
             Statement stmt = conn.createStatement()) {
            // Create the GD if not exists
            String createDatabaseSql = "CREATE SCHEMA IF NOT EXISTS `flowershop` DEFAULT CHARACTER SET utf8mb4;";
            stmt.executeUpdate(createDatabaseSql);

            // Use this database
            String useDatabaseSql = "USE `flowershop`;";
            stmt.executeUpdate(useDatabaseSql);

            // create the flowershop table
            String createFlowerShopSql = "CREATE TABLE `flowershop` ("
                    + "`id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`name` VARCHAR(50) NOT NULL,"
                    + "PRIMARY KEY (`id`)"
                    + ");";
            stmt.executeUpdate(createFlowerShopSql);

            // create the products table
            String createProductsSql = "CREATE TABLE `products` ("
                    + "`id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`name` VARCHAR(255) NOT NULL,"
                    + "`price` DECIMAL(10,2) NOT NULL,"
                    + "`stock` INT(11) NOT NULL,"
                    + "`ref` varchar(20) NOT NULL,"
                    + "PRIMARY KEY (`id`)"
                    + ");";
            stmt.executeUpdate(createProductsSql);

            // create trees table
            String createTreesSql = "CREATE TABLE `trees` ("
                    + "`id` INT(11) NOT NULL,"
                    + "`height` DECIMAL(5,2) NOT NULL,"
                    + "PRIMARY KEY (`id`),"
                    + "FOREIGN KEY (`id`) REFERENCES `products`(`id`) ON DELETE CASCADE"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            stmt.executeUpdate(createTreesSql);

            // create flowers table
            String createFlowersSql = "CREATE TABLE `flowers` ("
                    + "`id` INT(11) NOT NULL,"
                    + "`color` VARCHAR(50) NOT NULL,"
                    + "PRIMARY KEY (`id`),"
                    + "FOREIGN KEY (`id`) REFERENCES `products`(`id`) ON DELETE CASCADE"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            stmt.executeUpdate(createFlowersSql);

            // create decorations table
            String createDecorationsSql = "CREATE TABLE `decorations` ("
                    + "`id` INT(11) NOT NULL,"
                    + "`material` ENUM('plastico', 'madera') NOT NULL,"
                    + "PRIMARY KEY (`id`),"
                    + "FOREIGN KEY (`id`) REFERENCES `products`(`id`) ON DELETE CASCADE"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            stmt.executeUpdate(createDecorationsSql);

            // create tickets table
            String createTicketsSql = "CREATE TABLE `tickets` (\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `date` DATETIME NOT NULL,\n" +
                    "  `client` long,\n" +
                    "  `amount` DECIMAL(10,2) NOT NULL,\n" +
                    "  `finished` boolean,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            stmt.executeUpdate(createTicketsSql);

            // create ticket_details table
            String createTicketDetailsSql = "CREATE TABLE `ticket_details` (\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `ticket_id` INT(11) NOT NULL,\n" +
                    "  `product_id` INT(11) NOT NULL,\n" +
                    "  `ref`VARCHAR(20),\n" +
                    "  `quantity` INT(11) NOT NULL,\n" +
                    "  `price` DECIMAL(10,2),\n" +
                    "  `amount` DECIMAL(10,2),\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  FOREIGN KEY (`ticket_id`) REFERENCES `tickets`(`id`) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (`product_id`) REFERENCES `products`(`id`) ON DELETE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            stmt.executeUpdate(createTicketDetailsSql);
        } catch (SQLException e) {
            System.out.println("BD already exists.");
        }
    }

}
