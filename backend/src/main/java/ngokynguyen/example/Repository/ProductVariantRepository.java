package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Integer> {

    List<ProductVariant> findByProductId(Integer productId);

    List<ProductVariant> findByProductIdAndStatus(
            Integer productId,
            Integer status
    );

    List<ProductVariant> findByProductIdAndSize(
            Integer productId,
            String size
    );

    List<ProductVariant> findByProductIdAndColor(
            Integer productId,
            String color
    );
}