package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Category;
import ngokynguyen.example.Repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Category> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Category getById(
            @PathVariable Integer id) {

        return repository.findById(id)
                .orElseThrow();
    }
}