package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Category;
import ngokynguyen.example.Entity.Product;
import ngokynguyen.example.Repository.CategoryRepository;
import ngokynguyen.example.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm đang hoạt động
    public List<Product> getActiveProducts() {
        return productRepository.findByStatus(1);
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy sản phẩm có id: " + id
                        )
                );
    }

    // Lấy sản phẩm theo slug
    public Product getBySlug(String slug) {
        return productRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy sản phẩm"
                        )
                );
    }

    // Lấy sản phẩm theo danh mục
    public List<Product> getByCategory(Integer categoryId) {
        return productRepository.findByCategoryIdAndStatus(
                categoryId,
                1
        );
    }

    // Tìm kiếm sản phẩm
    public List<Product> search(String keyword) {
        return productRepository
                .findByNameContainingIgnoreCase(keyword);
    }

    // Thêm sản phẩm
    public Product create(Product product) {

        if (product.getSlug() != null &&
                productRepository.existsBySlug(product.getSlug())) {

            throw new RuntimeException(
                    "Slug sản phẩm đã tồn tại"
            );
        }

        if (product.getCategory() != null) {

            Integer categoryId =
                    product.getCategory().getId();

            Category category =
                    categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Danh mục không tồn tại"
                                    )
                            );

            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product update(
            Integer id,
            Product product
    ) {

        Product existing = getProductById(id);

        existing.setName(product.getName());
        existing.setSlug(product.getSlug());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setDiscount(product.getDiscount());
        existing.setImage(product.getImage());
        existing.setStatus(product.getStatus());

        if (product.getCategory() != null) {

            Integer categoryId =
                    product.getCategory().getId();

            Category category =
                    categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Danh mục không tồn tại"
                                    )
                            );

            existing.setCategory(category);
        }

        return productRepository.save(existing);
    }

    // Xóa sản phẩm
    public void delete(Integer id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }
}