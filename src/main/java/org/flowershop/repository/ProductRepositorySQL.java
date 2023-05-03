package org.flowershop.repository;


import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;
import org.flowershop.exceptions.NegativeValueException;
import org.flowershop.utils.Scan.Scan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductRepositorySQL implements IProductRepository {
    private final Connection connection;

    public ProductRepositorySQL(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de MySQL: " + ex);
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex);
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void addProduct(Product product) {
        // Create SQL INSERT to 'products' table
        String sqlProduct = "INSERT INTO products (ref, name, price, stock) VALUES (?, ?, ?, ?)";

        // Create SQL INSERT for product type
        String sqlTree = "INSERT INTO trees (id, height) VALUES (?, ?)";
        String sqlFlower = "INSERT INTO flowers (id, color) VALUES (?, ?)";
        String sqlDecoration = "INSERT INTO decorations (id, material) VALUES (?, ?)";

        // Product SQL statement
        try {
            PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct, Statement.RETURN_GENERATED_KEYS);
            stmtProduct.setString(1, product.getRef());
            stmtProduct.setString(2, product.getName());
            stmtProduct.setDouble(3, product.getPrice());
            stmtProduct.setInt(4, product.getStock());

            // Execute product statement
            int rowsAffected = stmtProduct.executeUpdate();
            ResultSet rs = stmtProduct.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            // SQL statement based on product type
            if (product instanceof Tree tree) {
                PreparedStatement stmtTree = connection.prepareStatement(sqlTree);
                stmtTree.setInt(1, id);
                stmtTree.setFloat(2, tree.getHeight() );

                // Execute tree statement
                stmtTree.executeUpdate();

                // Close
                stmtTree.close();
            } else if (product instanceof Flower flower) {
                PreparedStatement stmtFlower = connection.prepareStatement(sqlFlower);
                stmtFlower.setInt(1, id);
                stmtFlower.setString(2, flower.getColor() );

                // Execute tree statement
                stmtFlower.executeUpdate();

                // Close
                stmtFlower.close();
            }else if (product instanceof Decoration decoration) {
                PreparedStatement stmtDecoration = connection.prepareStatement(sqlDecoration);
                stmtDecoration.setInt(1, id);
                stmtDecoration.setString(2, decoration.getType() );

                // Execute tree statement
                stmtDecoration.executeUpdate();

                // Close
                stmtDecoration.close();
            }
            stmtProduct.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Product getProductById(long id){
        Product product = null;
        String query = "SELECT p.id, p.ref, p.name, p.price, p.stock, t.height, f.color, d.material " +
                "FROM products p " +
                "LEFT JOIN trees t ON p.id = t.id " +
                "LEFT JOIN flowers f ON p.id = f.id " +
                "LEFT JOIN decorations d ON p.id = d.id " +
                "WHERE p.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            // Get type
            if (rs.next()) {
                long num_id = rs.getLong("id");
                String reference = rs.getString("ref");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                // TREE
                if (rs.getObject("height") != null) {
                    product = new Tree(reference, name, price, rs.getFloat("height") );
                    // FLOWER
                } else if (rs.getObject("color") != null) {
                    product = new Flower(reference, name, price, rs.getString("color"));
                    // DECORATION
                } else if (rs.getObject("material") != null) {
                    product = new Decoration(reference, name, price, rs.getString("material"));
                }
                // SETTING ID AND STOCK
                product.setId(num_id);
                try {
                    product.setStock(stock);
                } catch (NegativeValueException e) {
                    throw new RuntimeException(e);
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException("Error getting the product", e);
        }
        return product;
    }

    @Override
    public Product getProductByRef(String ref) {
        Product product = null;
        String query = "SELECT p.id, p.ref, p.name, p.price, p.stock, t.height, f.color, d.material " +
                "FROM products p " +
                "LEFT JOIN trees t ON p.id = t.id " +
                "LEFT JOIN flowers f ON p.id = f.id " +
                "LEFT JOIN decorations d ON p.id = d.id " +
                "WHERE p.ref = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, ref);
            ResultSet rs = stmt.executeQuery();

            // Get type
            if (rs.next()) {
                long id = rs.getLong("id");
                String reference = rs.getString("ref");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                // TREE
                if (rs.getObject("height") != null) {
                    product = new Tree(reference, name, price, rs.getFloat("height") );
                // FLOWER
                } else if (rs.getObject("color") != null) {
                    product = new Flower(reference, name, price, rs.getString("color"));
                // DECORATION
                } else if (rs.getObject("material") != null) {
                    product = new Decoration(reference, name, price, rs.getString("material"));
                }
                // SETTING ID AND STOCK
                product.setId(id);
                try {
                    product.setStock(stock);
                } catch (NegativeValueException e) {
                    throw new RuntimeException(e);
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException("Error getting the product", e);
        }
        return product;
    }

    @Override
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        String product_type  = "";
        String query = "SELECT p.id, p.ref, p.name, p.price, p.stock, t.height, f.color, d.material " +
                "FROM products p " +
                "LEFT JOIN trees t ON p.id = t.id " +
                "LEFT JOIN flowers f ON p.id = f.id " +
                "LEFT JOIN decorations d ON p.id = d.id";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            // Get type
            while (rs.next()) {
                if (rs.getObject("height") != null) {
                    product_type = "TREE";
                } else if (rs.getObject("color") != null) {
                    product_type = "FLOWER";
                } else if (rs.getObject("material") != null) {
                    product_type = "DECORATION";
                }

                switch (product_type) {
                    case "TREE":
                        Tree tree = new Tree();
                        tree.setId(rs.getLong("id"));
                        tree.setRef(rs.getString("ref"));
                        tree.setName(rs.getString("name"));
                        try {
                            tree.setPrice(rs.getDouble("price"));
                            tree.setStock(rs.getInt("stock"));
                        } catch (NegativeValueException e) {
                            throw new RuntimeException(e);
                        }
                        tree.setHeight(rs.getFloat("height"));
                        products.add(tree);
                        break;
                    case "FLOWER":
                        Flower flower = new Flower();
                        flower.setId(rs.getLong("id"));
                        flower.setRef(rs.getString("ref"));
                        flower.setName(rs.getString("name"));
                        try {
                            flower.setPrice(rs.getDouble("price"));
                            flower.setStock(rs.getInt("stock"));
                        } catch (NegativeValueException e) {
                            throw new RuntimeException(e);
                        }
                        flower.setColor(rs.getString("color"));
                        products.add(flower);
                        break;
                    case "DECORATION":
                        Decoration decoration = new Decoration();
                        decoration.setId(rs.getLong("id"));
                        decoration.setRef(rs.getString("ref"));
                        decoration.setName(rs.getString("name"));
                        try {
                            decoration.setPrice(rs.getDouble("price"));
                            decoration.setStock(rs.getInt("stock"));
                        } catch (NegativeValueException e) {
                            throw new RuntimeException(e);
                        }
                        decoration.setType(rs.getString("material"));
                        products.add(decoration);
                        break;
                    default:
                        throw new RuntimeException("Unknown product type.");
                }

            }

        }catch (SQLException e) {
            throw new RuntimeException("Error getting the product", e);
        }
        return products;
    }

    @Override
    public void updateProductById(long id, Product product) {

    }

    @Override
    public void updateProduct(Product product) {
        String query = "UPDATE products SET ref=?, name=?, price=?, stock=? WHERE id=?";
        // Update product
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getRef());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setLong(5, product.getId());
            stmt.executeUpdate();

            // Update type of product
            if (product instanceof Tree) {
                query = "UPDATE trees SET height=? WHERE id=?";
                try (PreparedStatement subStmt = connection.prepareStatement(query)) {
                    subStmt.setFloat(1, ((Tree) product).getHeight());
                    subStmt.setLong(2, product.getId());
                    subStmt.executeUpdate();
                }
            } else if (product instanceof Flower) {
                query = "UPDATE flowers SET color=? WHERE id=?";
                try (PreparedStatement subStmt = connection.prepareStatement(query)) {
                    subStmt.setString(1, ((Flower) product).getColor());
                    subStmt.setLong(2, product.getId());
                    subStmt.executeUpdate();
                }
            } else if (product instanceof Decoration) {
                query = "UPDATE decorations SET material=? WHERE id=?";
                try (PreparedStatement subStmt = connection.prepareStatement(query)) {
                    subStmt.setString(1, ((Decoration) product).getType());
                    subStmt.setLong(2, product.getId());
                    subStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeProductById(long id){
        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error deleting the product", e);
        }
    }

    @Override
    public void removeProductByRef(String ref) {
        String query = "DELETE FROM products WHERE ref = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ref);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error deleting the product", e);
        }
    }

    @Override
    public void saveProducts() {
        // Not implemented
    }

    @Override
    public void loadProducts() {
        // Not implemented
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Failed to close connection:: " + ex);
            throw new RuntimeException(ex);
        }
    }
}
