package org.flowershop.repository.repositoryMongoDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.products.Decoration;
import org.flowershop.domain.products.Flower;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.products.Tree;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.IFlowerShopRepository;
import org.flowershop.repository.IProductRepository;
import org.flowershop.repository.ITicketRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// TODO: MongoDb not implemented yet
public class MongoDbRepository implements IFlowerShopRepository, IProductRepository, ITicketRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collectionFlowerShop;
    private final MongoCollection<Document> collectionProducts;
    private final MongoCollection<Document> collectionTickets;
    private Document document;
    private ObjectMapper objectMapper;

    public MongoDbRepository() {
        String connectionString = "mongodb+srv://<...>.mongodb.net/?retryWrites=true&w=majority";
        String dbName = "FlowerShop";
        this.mongoClient = MongoClients.create(connectionString);
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
            // Convert the FlowerShop object to a JSON document
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
        Product product;
        try {
            product = objectMapper.readValue(document.toJson(), Product.class);
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

    }

    @Override
    public void updateProduct(Product product) {
        if (product instanceof Tree tree) {
            // TODO: update if tree
        } else if (product instanceof Flower flower) {
            // TODO: update if flower
        } else if (product instanceof Decoration decoration) {
            // TODO: update if decoration
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
        Ticket ticket;
        try {
            ticket = objectMapper.readValue(document.toJson(), Ticket.class);
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
            Ticket ticket;
            try {
                ticket = objectMapper.readValue(doc.toJson(), Ticket.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public Long getNewTicketId() {
        FindIterable<Document> iterable = collectionTickets.find()
                .sort(Sorts.descending("id"))
                .limit(1);
        Document document = iterable.first();
        Integer maxId = document.getInteger("id");
        long longMaxId = maxId.longValue();
        return Long.valueOf(longMaxId + 1L);
    }

    @Override
    public Ticket removeTicketById(long id) throws IOException {
        Document query = new Document("id", id);
        Document document = collectionTickets.findOneAndDelete(query);
        Ticket ticket;
        try {
            ticket = objectMapper.readValue(document.toJson(), Ticket.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ticket;
    }

}
