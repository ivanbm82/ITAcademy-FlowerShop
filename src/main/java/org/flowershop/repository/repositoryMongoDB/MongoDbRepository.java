package org.flowershop.repository.repositoryMongoDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.IFlowerShopRepository;
import org.flowershop.repository.IProductRepository;
import org.flowershop.repository.ITicketRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class MongoDbRepository implements IFlowerShopRepository, IProductRepository, ITicketRepository {
    // Config properties
    private final Properties properties;
    private Document document;
    private ObjectMapper objectMapper;
    // Connection to db
    private String connection;
    private String dbName;
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    // Collections
    private final MongoCollection<Document> collectionFlowerShop;
    private final MongoCollection<Document> collectionProducts;
    private final MongoCollection<Document> collectionTickets;


    public MongoDbRepository() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection = properties.getProperty("uri_mongodb");
        dbName = properties.getProperty("dbName");
        this.mongoClient = MongoClients.create(connection);
        this.database = mongoClient.getDatabase(dbName);
        this.collectionFlowerShop = database.getCollection("flowershop");
        this.collectionProducts = database.getCollection("products");
        this.collectionTickets = database.getCollection("tickets");

        objectMapper = new ObjectMapper();
    }

    // Method to save the name of the flower shop
    public void addFlowerShop(FlowerShop flowerShop) {
        String json;
        try {
            json = objectMapper.writeValueAsString(flowerShop);
            document = Document.parse(json);
            collectionFlowerShop.insertOne(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // This method returns the name of the flower shop if exists.
    public List<FlowerShop> getAllFlowerShops() {
        List<FlowerShop> flowerShops = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("flowershop");

        for (Document doc : collection.find()) {
            FlowerShop flowerShop;
            try {
                flowerShop = objectMapper.readValue(doc.toJson(), FlowerShop.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            flowerShops.add(flowerShop);
        }

        return flowerShops;
    }


    // Method to insert a new product document
    public void addProduct(Product product) {
        try {
            // Convert the product object to a JSON document
            String json = objectMapper.writeValueAsString(product);
            Document document = Document.parse(json);

            // Insert the document into the collection.
            collectionProducts.insertOne(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(long id) {
        Document query = new Document("id", id);
        Document document = collectionProducts.find(query).first();
        Product product;
        try {
            product = objectMapper.readValue(document.toJson(), Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public Product getProductByRef(String ref) {
        Document query = new Document("ref", ref);
        Document document = collectionProducts.find(query).first();

        Product product = null;
        try {
            if (document != null) {
                product = objectMapper.readValue(document.toJson(), Product.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("products");

        for (Document doc : collection.find()) {
            Product product;
            try {
                product = objectMapper.readValue(doc.toJson(), Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            products.add(product);
        }

        return products;
    }

    @Override
    public void updateProductById(long id, Product product) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(product);
            Document document = Document.parse(json);
            collectionProducts.updateOne(Filters.eq("id", id), new Document("$set", document) );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProduct(Product product) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(product);
            Document document = Document.parse(json);
            collectionProducts.updateOne(Filters.eq("ref", product.getRef()), new Document("$set", document) );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeProductById(long id) {
        Document query = new Document("id", id);
        collectionProducts.deleteOne(query);
    }

    @Override
    public void removeProductByRef(String ref) {
        Document query = new Document("ref", ref);
        collectionProducts.deleteOne(query);
    }

    @Override
    public void saveProducts() {
        // TODO: saveProduct() not implemented
    }

    @Override
    public void loadProducts() {
        // TODO: loadProducts() not implemented
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        try {
            // Convert the FlowerShop object to a JSON document
            String json = objectMapper.writeValueAsString(ticket);
            Document document = Document.parse(json);

            // Insert the document into the collection.
            collectionTickets.insertOne(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ticket;
    }

    @Override
    public Ticket getTicketById(Long id) {
        Document query = new Document("id", id);
        Document document = collectionTickets.find(query).first();
        Ticket ticket = null;
        try {
            if ( document != null) {
                ticket = objectMapper.readValue(document.toJson(), Ticket.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("tickets");

        for (Document doc : collection.find()) {
            Ticket ticket = null;
            try {
                if ( document != null) {
                    ticket = objectMapper.readValue(document.toJson(), Ticket.class);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public Long getNewTicketId() {
        Integer maxId ;
        long longMaxId = 0L;
        FindIterable<Document> iterable = collectionTickets.find()
                .sort(Sorts.descending("id"))
                .limit(1);
        Document document = iterable.first();
        if ( document != null ) {
            maxId = document.getInteger("id");
            longMaxId = maxId.longValue();
        }
        return Long.valueOf(longMaxId + 1L);
    }

    @Override
    public Ticket removeTicketById(long id) throws IOException {
        Document query = new Document("id", id);
        Document document = collectionTickets.findOneAndDelete(query);
        Ticket ticket = null;
        try {
            if ( document != null) {
                ticket = objectMapper.readValue(document.toJson(), Ticket.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ticket;
    }

}
