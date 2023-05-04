package org.flowershop.repository;

import org.flowershop.domain.tickets.Ticket;
import org.flowershop.domain.tickets.TicketDetail;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketRepositorySQL implements ITicketRepository {
    private final Connection connection;
    private final Properties properties;
    private String uri;
    private String user;
    private String password;

    public TicketRepositorySQL() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uri = properties.getProperty("uri_sql");
        user = properties.getProperty("user");
        password = properties.getProperty("pass");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(uri, user, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de MySQL: " + ex);
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tickets (date, client, amount, finished) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(ticket.getDate().getTime()));
            statement.setLong(2, ticket.getClient());
            statement.setDouble(3, ticket.getAmount());
            statement.setBoolean(4, ticket.getFinished());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long id = resultSet.getLong(1);
            ticket.setId(id);

            for (TicketDetail ticketDetail : ticket.getTicketDetailList()) {
                PreparedStatement detailStatement = connection.prepareStatement(
                        "INSERT INTO ticket_details (ticket_id, product_id, ref, quantity, price, amount) VALUES (?, ?, ?, ?, ?, ?)");
                detailStatement.setLong(1, id);
                detailStatement.setLong(2, ticketDetail.getIdProduct());
                detailStatement.setString(3, ticketDetail.getRef());
                detailStatement.setInt(4, ticketDetail.getQuantity());
                detailStatement.setDouble(5, ticketDetail.getPrice());
                detailStatement.setDouble(6, ticketDetail.getAmount());
                detailStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println("Error al agregar ticket: " + ex);
            throw new RuntimeException(ex);
        }
        return ticket;
    }


    @Override
    public Ticket getTicketById(Long id) {
        String sql = "SELECT * FROM tickets WHERE id=?";
        Ticket ticket = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ticket = new Ticket(rs.getLong("id"), rs.getDate("date"),
                        rs.getLong("client"), rs.getDouble("amount"),
                        rs.getBoolean("finished"));
                String ticketDetailSql = "SELECT * FROM ticket_details WHERE ticket_id=?";
                try (PreparedStatement pstmt2 = connection.prepareStatement(ticketDetailSql)) {
                    pstmt2.setLong(1, id);
                    ResultSet tdRs = pstmt2.executeQuery();
                    while (tdRs.next()) {
                        TicketDetail td = new TicketDetail(tdRs.getLong("product_id"),
                                tdRs.getString("ref"),
                                tdRs.getInt("quantity"),
                                tdRs.getDouble("price"),
                                tdRs.getDouble("amount"));
                        ticket.addTicketDetail(td);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al obtener los detalles del ticket con id " + id + ": " + ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el ticket con id " + id + ": " + ex);
        }
        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> ticketList = new ArrayList<>();

        try {
            String query = "SELECT * FROM tickets";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Date date = resultSet.getDate("date");
                Long client = resultSet.getLong("client");
                Double amount = resultSet.getDouble("amount");
                Boolean finished = resultSet.getBoolean("finished");

                Ticket ticket = new Ticket(id, date, client, amount, finished);

                String ticketDetailQuery = "SELECT * FROM ticket_details WHERE ticket_id = ?";
                PreparedStatement ticketDetailStatement = connection.prepareStatement(ticketDetailQuery);
                ticketDetailStatement.setLong(1, id);

                ResultSet ticketDetailResultSet = ticketDetailStatement.executeQuery();

                while (ticketDetailResultSet.next()) {
                    Long productId = ticketDetailResultSet.getLong("product_id");
                    String ref = ticketDetailResultSet.getString("ref");
                    Integer quantity = ticketDetailResultSet.getInt("quantity");
                    Double price = ticketDetailResultSet.getDouble("price");
                    Double ticketDetailAmount = ticketDetailResultSet.getDouble("amount");

                    TicketDetail ticketDetail = new TicketDetail(productId, ref, quantity, price, ticketDetailAmount);

                    ticket.addTicketDetail(ticketDetail);
                }

                ticketList.add(ticket);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener todos los tickets: " + ex);
            throw new RuntimeException(ex);
        }

        return ticketList;
    }

    @Override
    public Long getNewTicketId() {
        return null;
    }

    @Override
    public Ticket removeTicketById(long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tickets WHERE id = ?");
            ps.setLong(1, id);
            int result = ps.executeUpdate();
            if (result == 0) {
                System.out.println("Ticket with ID " + id + " not found.");
                return null;
            } else {
                System.out.println("Ticket with ID " + id + " removed.");
                return new Ticket();
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar el ticket con id " + id + ": " + ex);
            throw new RuntimeException(ex);
        }
    }

}

