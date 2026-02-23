package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductController controller;
    private ProductService service;
    private Model model;

    @BeforeEach
    void setup() throws Exception {
        controller = new ProductController();
        service = mock(ProductService.class);
        model = mock(Model.class);

        // Inject mock service into private field using reflection
        Field serviceField = ProductController.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(controller, service);
    }

    @Test
    void createProductPage_shouldReturnView() {
        String view = controller.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", view);
    }

    @Test
    void createProductPost_shouldCallService_andRedirect() {
        Product p = new Product();

        String view = controller.createProductPost(p, model);

        verify(service).create(p);
        assertEquals("redirect:list", view);
    }

    @Test
    void productListPage_shouldAddProductsToModel_andReturnView() {
        when(service.findAll()).thenReturn(List.of(new Product()));

        String view = controller.productListPage(model);

        verify(service).findAll();
        verify(model).addAttribute(eq("products"), any());
        assertEquals("productList", view);
    }

    @Test
    void editProductPage_shouldAddProductToModel_andReturnView() {
        Product p = new Product();
        when(service.findById("P-1")).thenReturn(p);

        String view = controller.editProductPage("P-1", model);

        verify(service).findById("P-1");
        verify(model).addAttribute("product", p);
        assertEquals("editProduct", view);
    }

    @Test
    void editProductPost_shouldCallServiceEdit_andRedirect() {
        Product p = new Product();

        String view = controller.editProductPost(p);

        verify(service).edit(p);
        assertEquals("redirect:list", view);
    }

    @Test
    void deleteProduct_shouldCallServiceDelete_andRedirect() {
        String view = controller.deleteProduct("P-1");

        verify(service).delete("P-1");
        assertEquals("redirect:../list", view);
    }
}