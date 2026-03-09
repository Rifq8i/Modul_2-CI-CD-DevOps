package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    @Setter
    String status;

    // Constructor dengan 4 parameter
    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    // Constructor dengan 5 parameter
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
    }
}