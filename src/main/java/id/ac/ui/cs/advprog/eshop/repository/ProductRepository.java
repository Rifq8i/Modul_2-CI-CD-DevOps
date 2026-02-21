package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    public ProductRepository() {
        super();
    }

    public Product create(final Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(final String productId) {
        Product found = null;
        for (final Product product : productData) {
            if (product.getProductId().equals(productId)) {
                found = product;
                break;
            }
        }
        return found;
    }

    public Product edit(final Product product) {
        Product updated = null;
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(product.getProductId())) {
                productData.set(i, product);
                updated = product;
                break;
            }
        }
        return updated;
    }

    public void delete(final String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }
}