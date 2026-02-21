package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl service;
    private ProductRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        service = new ProductServiceImpl();
        repository = mock(ProductRepository.class);

        // inject mock repo into private field
        Field repoField = ProductServiceImpl.class.getDeclaredField("productRepository");
        repoField.setAccessible(true);
        repoField.set(service, repository);
    }

    @Test
    void create_shouldCallRepositoryCreate_andReturnSameProduct() {
        Product product = new Product();
        product.setProductId("P-1");
        product.setProductName("Keyboard");
        product.setProductQuantity(10);

        Product result = service.create(product);

        verify(repository, times(1)).create(product);
        assertSame(product, result); // service returns same object
    }

    @Test
    void findAll_shouldCollectIteratorIntoList() {
        Product p1 = new Product();
        p1.setProductId("P-1");
        p1.setProductName("Keyboard");
        p1.setProductQuantity(10);

        Product p2 = new Product();
        p2.setProductId("P-2");
        p2.setProductName("Mouse");
        p2.setProductQuantity(5);

        Iterator<Product> iterator = List.of(p1, p2).iterator();
        when(repository.findAll()).thenReturn(iterator);

        List<Product> result = service.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals("P-1", result.get(0).getProductId());
        assertEquals("P-2", result.get(1).getProductId());
    }

    @Test
    void findById_shouldReturnRepositoryResult() {
        Product product = new Product();
        product.setProductId("P-1");

        when(repository.findById("P-1")).thenReturn(product);

        Product result = service.findById("P-1");

        verify(repository, times(1)).findById("P-1");
        assertSame(product, result);
    }

    @Test
    void edit_shouldCallRepositoryEdit() {
        Product product = new Product();
        product.setProductId("P-1");

        service.edit(product);

        verify(repository, times(1)).edit(product);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        service.delete("P-1");

        verify(repository, times(1)).delete("P-1");
    }
}