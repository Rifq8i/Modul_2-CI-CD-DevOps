package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    public ProductController() {
        // default constructor
        super();
    }

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(final Model model) {
        final Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute final Product product, final Model model) {
        service.create(product);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(final Model model) {
        final List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable final String productId, final Model model) {
        final Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute final Product product) {
        service.edit(product);
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable final String productId) {
        service.delete(productId);
        return "redirect:../list";
    }
}