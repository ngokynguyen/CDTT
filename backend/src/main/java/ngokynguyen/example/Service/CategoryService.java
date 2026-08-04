package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Category;
import ngokynguyen.example.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByStatus(1);
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy danh mục"));
    }

    public Category getBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy danh mục"));
    }

    public List<Category> getByParentId(Integer parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public Category create(Category category) {

        if (category.getSlug() != null &&
                categoryRepository.existsBySlug(category.getSlug())) {

            throw new RuntimeException("Slug danh mục đã tồn tại");
        }

        return categoryRepository.save(category);
    }

    public Category update(Integer id, Category category) {

        Category existing = getById(id);

        if (category.getName() != null) {
            existing.setName(category.getName());
        }

        if (category.getSlug() != null) {
            existing.setSlug(category.getSlug());
        }

        existing.setDescription(category.getDescription());
        existing.setParentId(category.getParentId());
        existing.setSortOrder(category.getSortOrder());
        existing.setStatus(category.getStatus());

        return categoryRepository.save(existing);
    }

    public void delete(Integer id) {

        Category category = getById(id);

        categoryRepository.delete(category);
    }
}