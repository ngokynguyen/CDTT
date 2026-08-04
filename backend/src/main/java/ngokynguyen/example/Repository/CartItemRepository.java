package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CartItemRepository
        extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCartId(Integer cartId);

    Optional<CartItem> findByCartIdAndVariantId(
            Integer cartId,
            Integer variantId
    );

    void deleteByCartId(Integer cartId);
}