package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Cart;
import ngokynguyen.example.Entity.CartItem;
import ngokynguyen.example.Entity.ProductVariant;
import ngokynguyen.example.Entity.User;
import ngokynguyen.example.Repository.CartItemRepository;
import ngokynguyen.example.Repository.CartRepository;
import ngokynguyen.example.Repository.ProductVariantRepository;
import ngokynguyen.example.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository variantRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductVariantRepository variantRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.variantRepository = variantRepository;
    }

    public Cart getOrCreateCart(Integer userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    User user =
                            userRepository.findById(userId)
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Không tìm thấy người dùng"
                                            ));

                    Cart cart = new Cart();

                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    public List<CartItem> getItems(Integer userId) {

        Cart cart = getOrCreateCart(userId);

        return cartItemRepository.findByCartId(
                cart.getId()
        );
    }

    public CartItem addItem(
            Integer userId,
            Integer variantId,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Số lượng không hợp lệ"
            );
        }

        Cart cart = getOrCreateCart(userId);

        ProductVariant variant =
                variantRepository.findById(variantId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy sản phẩm"
                                ));

        if (variant.getQuantity() < quantity) {

            throw new RuntimeException(
                    "Sản phẩm không đủ tồn kho"
            );
        }

        var existing =
                cartItemRepository
                        .findByCartIdAndVariantId(
                                cart.getId(),
                                variantId
                        );

        if (existing.isPresent()) {

            CartItem item = existing.get();

            int newQuantity =
                    item.getQuantity() + quantity;

            if (newQuantity > variant.getQuantity()) {

                throw new RuntimeException(
                        "Số lượng vượt quá tồn kho"
                );
            }

            item.setQuantity(newQuantity);

            return cartItemRepository.save(item);
        }

        CartItem item = new CartItem();

        item.setCart(cart);
        item.setVariant(variant);
        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

    public CartItem updateItem(
            Integer itemId,
            Integer quantity
    ) {

        if (quantity == null || quantity <= 0) {

            throw new RuntimeException(
                    "Số lượng không hợp lệ"
            );
        }

        CartItem item =
                cartItemRepository.findById(itemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy sản phẩm trong giỏ"
                                ));

        if (quantity >
                item.getVariant().getQuantity()) {

            throw new RuntimeException(
                    "Số lượng vượt quá tồn kho"
            );
        }

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

    public void removeItem(Integer itemId) {

        CartItem item =
                cartItemRepository.findById(itemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy sản phẩm trong giỏ"
                                ));

        cartItemRepository.delete(item);
    }

    public void clearCart(Integer userId) {

        Cart cart = getOrCreateCart(userId);

        cartItemRepository.deleteByCartId(
                cart.getId()
        );
    }
}