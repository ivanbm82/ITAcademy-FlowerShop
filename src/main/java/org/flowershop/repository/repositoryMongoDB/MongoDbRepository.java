package org.flowershop.repository.repositoryMongoDB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;
import org.flowershop.domain.flowerShop.FlowerShop;
import org.flowershop.domain.products.Product;
import org.flowershop.domain.tickets.Ticket;
import org.flowershop.repository.IFlowerShopRepository;
import org.flowershop.repository.IProductRepository;
import org.flowershop.repository.ITicketRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// TODO: MongoDb not implemented yet
public class MongoDbRepository implements IFlowerShopRepository, IProductRepository, ITicketRepository {
    String connectionString = "mongodb+srv://...";
    String dbName = "FlowerShop";

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collectionFlowerShop;
    private final MongoCollection<Document> collectionProducts;
    private final MongoCollection<Document> collectionTickets;
    private Document document;
    private ObjectMapper objectMapper;

    public MongoDbRepository() {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(dbName);
        this.collectionFlowerShop = database.getCollection("flowershop");
        this.collectionProducts = database.getCollection("products");
        this.collectionTickets = database.getCollection("tickets");

        objectMapper = new ObjectMapper();
    }

    // Method to save the name of the flower shop
    public void addFlowerShop(FlowerShop flowerShop) {

        document = new Document("name", flowerShop.getName());
        collectionFlowerShop.insertOne(document);
    }

    // This method returns the name of the flower shop if exists.
    public List<FlowerShop> getAllFlowerShops() {
        MongoCollection<Document> collection = database.getCollection("flowershop");
        List<FlowerShop> flowerShops = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                try {
                    FlowerShop flowerShop = objectMapper.readValue(document.toJson(), FlowerShop.class);
                    System.out.println(flowerShop);
                    //flowerShops.add(flowerShop);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return flowerShops;
        }
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
            // Exception
        }
    }

    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public Product getProductByRef(String ref) {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public void updateProductById(long id, Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void removeProductById(long id) {

    }

    @Override
    public void removeProductByRef(String ref) {

    }

    @Override
    public void saveProducts() {

    }

    @Override
    public void loadProducts() {

    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        return null;
    }

    @Override
    public Ticket getTicketById(Long id) {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

    @Override
    public Long getNewTicketId() {
        return null;
    }

    @Override
    public Ticket removeTicketById(long id) throws IOException {
        return null;
    }

}
