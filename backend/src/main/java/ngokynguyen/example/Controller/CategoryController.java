package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Category;
import ngokynguyen.example.Repository.CategoryRepository;
import ngokynguyen.example.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(
            @PathVariable("id") Integer id) {

        return categoryService.getById(id);
    }

    @GetMapping("/slug/{slug}")
    public Category getBySlug(
            @PathVariable("slug") String slug) {

        return categoryService.getBySlug(slug);
    }
}