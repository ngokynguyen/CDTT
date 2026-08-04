package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Product> findByStatus(Integer status);

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findByCategoryIdAndStatus(
            Integer categoryId,
            Integer status
    );

    List<Product> findByNameContainingIgnoreCase(String name);
}