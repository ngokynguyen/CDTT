package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Integer> {

    List<ProductVariant> findByProductId(Integer productId);

    List<ProductVariant> findByProductIdAndStatus(
            Integer productId,
            Integer status
    );

}