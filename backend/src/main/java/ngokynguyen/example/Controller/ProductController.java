package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Product;
import ngokynguyen.example.Repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Integer id,
            @RequestBody Product product) {

        Product oldProduct = repository.findById(id)
                .orElseThrow();

        oldProduct.setName(product.getName());
        oldProduct.setSlug(product.getSlug());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setDiscount(product.getDiscount());
        oldProduct.setImage(product.getImage());
        oldProduct.setStatus(product.getStatus());

        return repository.save(oldProduct);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}