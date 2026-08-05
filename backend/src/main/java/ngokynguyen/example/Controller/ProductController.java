package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Product;
import ngokynguyen.example.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable("id") Integer id
    ) {
        return productService.getProductById(id);
    }

    @GetMapping("/active")
    public List<Product> getActiveProducts() {
        return productService.getActiveProducts();
    }

    @GetMapping("/slug/{slug}")
    public Product getBySlug(
            @PathVariable("slug") String slug
    ) {
        return productService.getBySlug(slug);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(
            @PathVariable("categoryId") Integer categoryId
    ) {
        return productService.getByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<Product> search(
            @RequestParam("keyword") String keyword
    ) {
        return productService.search(keyword);
    }

    @PostMapping
    public Product create(
            @RequestBody Product product
    ) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable("id") Integer id,
            @RequestBody Product product
    ) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") Integer id
    ) {
        productService.delete(id);
        return "Xóa sản phẩm thành công";
    }
}