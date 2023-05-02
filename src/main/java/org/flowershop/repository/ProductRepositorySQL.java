package org.flowershop.repository;


import org.flowershop.domain.products.Product;
import java.math.BigDecimal;
import java.sql.*;
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



        public void updateProductById() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Introduzca el ID del producto que quieres actualizar:");
            int id = scanner.nextInt();

            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM product WHERE id = ?")) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Producto con ID " + id + " no existe.");
                        return;
                    }

                    System.out.println("Producto encontrado con ID " + id + ":");
                    System.out.println("Nombre: " + rs.getString("name"));
                    System.out.println("Precio: " + rs.getBigDecimal("price"));
                    System.out.println("Stock: " + rs.getInt("stock"));

                    System.out.print("¿Quieres actualizar el nombre del producto? (S/N) ");
                    String input = scanner.next();

                    if (input.equalsIgnoreCase("S")) {
                        System.out.print("Introduzca el nuevo nombre del producto: ");
                        String name = scanner.next();

                        try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE product SET name = ? WHERE id = ?")) {
                            updateStmt.setString(1, name);
                            updateStmt.setInt(2, id);
                            updateStmt.executeUpdate();

                            System.out.println("Nombre del producto actualizado con éxito.");
                        }
                    }

                    System.out.print("¿Quieres actualizar el precio del producto? (S/N) ");
                    input = scanner.next();

                    if (input.equalsIgnoreCase("S")) {
                        System.out.print("Introduzca el nuevo precio del producto: ");
                        BigDecimal price = scanner.nextBigDecimal();

                        try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE product SET price = ? WHERE id = ?")) {
                            updateStmt.setBigDecimal(1, price);
                            updateStmt.setInt(2, id);
                            updateStmt.executeUpdate();

                            System.out.println("Precio del producto actualizado con éxito.");
                        }
                    }

                    System.out.print("¿Quieres actualizar el stock del producto? (S/N) ");
                    input = scanner.next();

                    if (input.equalsIgnoreCase("S")) {
                        System.out.print("Introduzca la nueva cantidad de stock del producto: ");
                        int stock = scanner.nextInt();

                        try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE product SET stock = ? WHERE id = ?")) {
                            updateStmt.setInt(1, stock);
                            updateStmt.setInt(2, id);
                            updateStmt.executeUpdate();

                            System.out.println("Stock del producto actualizado con éxito.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al actualizar el producto con ID " + id + ": " + e);
            }
        }


        @Override
        public void removeProductById(long id){
            String query = "DELETE FROM product WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException("Error al eliminar producto", e);
            }
        }

        public void addProduct() {
            Scanner sc = new Scanner(System.in);

            // Obtener los datos del producto por pantalla
            System.out.print("Nombre del producto: ");
            String name = sc.nextLine();

            System.out.print("Precio del producto: ");
            double price = sc.nextDouble();

            System.out.print("Cantidad en stock: ");
            int stock = sc.nextInt();

            // Comprobar si ya existe el producto
            boolean exists = false;
            try {
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT COUNT(*) AS count FROM product WHERE name = ?"
                );
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    exists = rs.getInt("count") > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            if (exists) {
                System.out.println("Error: El producto ya existe");
                return;
            }

            // Insertar el producto en la tabla 'product'
            int productId = -1;
            try {
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO product (name, price, stock) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                stmt.setString(1, name);
                stmt.setDouble(2, price);
                stmt.setInt(3, stock);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    productId = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            // Insertar datos adicionales según el tipo de producto
            String type = "";
            while (!type.equals("tree") && !type.equals("flower") && !type.equals("decoration")) {
                System.out.print("Tipo de producto (tree/flower/decoration): ");
                type = sc.next();
            }

            try {
                PreparedStatement stmt = null;
                switch (type) {
                    case "tree":
                        stmt = connection.prepareStatement(
                                "INSERT INTO tree (id, height) VALUES (?, ?)"
                        );
                        break;
                    case "flower":
                        stmt = connection.prepareStatement(
                                "INSERT INTO flower (id, color) VALUES (?, ?)"
                        );
                        break;
                    case "decoration":
                        stmt = connection.prepareStatement(
                                "INSERT INTO decoration (id, material) VALUES (?, ?)"
                        );
                        break;
                }

                stmt.setInt(1, productId);

                switch (type) {
                    case "tree":
                        System.out.print("Altura del árbol: ");
                        double height = sc.nextDouble();
                        stmt.setDouble(2, height);
                        break;
                    case "flower":
                        System.out.print("Color de la flor: ");
                        String color = sc.next();
                        stmt.setString(2, color);
                        break;
                    case "decoration":
                        System.out.print("Material de la decoración (plastico/madera): ");
                        String material = sc.next();
                        stmt.setString(2, material);
                        break;
                }

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            System.out.println("Producto añadido correctamente");
        }


        @Override
        public void addProduct(Product product) {

        }

        @Override
        public Product getProductById(long id){
            return null;
        }

        @Override
        public void saveProducts() {
            // Implementar el guardado de productos si es necesario
        }

        @Override
        public void loadProducts() {
            // Implementar la carga de productos si es necesario
        }

        @Override
        public Product getProductByRef(String ref) {
            // Implementar la obtención de productos por referencia si es necesario
            return null;
        }

        @Override
        public List<Product> getProducts(){
            return null;
        }

        @Override
        public void updateProductById(long id, Product product) {

        }

        @Override
        public void removeProductByRef(String ref) {
            // Implementar la eliminación de productos por referencia si es necesario
        }
        public void close() {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex);
                throw new RuntimeException(ex);
            }
        }
    }
