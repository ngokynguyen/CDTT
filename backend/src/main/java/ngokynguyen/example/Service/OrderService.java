package ngokynguyen.example.Service;

import jakarta.transaction.Transactional;
import ngokynguyen.example.Entity.*;
import ngokynguyen.example.Repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository variantRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            AddressRepository addressRepository,
            ProductVariantRepository variantRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.variantRepository = variantRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Integer id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy đơn hàng"
                        ));
    }

    public List<Order> getByUserId(Integer userId) {

        return orderRepository.findByUserId(userId);
    }

    public List<Order> getByStatus(
            String status
    ) {

        return orderRepository.findByStatus(status);
    }

    @Transactional
    public Order createFromCart(
            Integer userId,
            Integer addressId,
            String paymentMethod
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy người dùng"
                                ));

        Address address =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy địa chỉ"
                                ));

        if (!address.getUser().getId().equals(userId)) {

            throw new RuntimeException(
                    "Địa chỉ không thuộc người dùng"
            );
        }

        Cart cart =
                cartRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Giỏ hàng không tồn tại"
                                ));

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(
                        cart.getId()
                );

        if (cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Giỏ hàng đang trống"
            );
        }

        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PENDING");
        order.setTotalAmount(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal total =
                BigDecimal.ZERO;

        List<OrderItem> orderItems =
                new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            ProductVariant variant =
                    variantRepository.findById(
                            cartItem.getVariant().getId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Sản phẩm không tồn tại"
                            ));

            int quantity =
                    cartItem.getQuantity();

            if (variant.getQuantity() < quantity) {

                throw new RuntimeException(
                        "Sản phẩm "
                        + variant.getProduct().getName()
                        + " không đủ tồn kho"
                );
            }

            BigDecimal price =
                    variant.getProduct().getPrice();

            BigDecimal itemTotal =
                    price.multiply(
                            BigDecimal.valueOf(quantity)
                    );

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);
            orderItem.setVariant(variant);
            orderItem.setPrice(price);
            orderItem.setQuantity(quantity);

            orderItems.add(orderItem);

            total = total.add(itemTotal);

            variant.setQuantity(
                    variant.getQuantity() - quantity
            );

            variantRepository.save(variant);
        }

        order.setTotalAmount(total);

        order = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteByCartId(
                cart.getId()
        );

        return order;
    }

    @Transactional
    public Order updateStatus(
            Integer orderId,
            String status
    ) {

        Order order = getById(orderId);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public List<OrderItem> getOrderItems(
            Integer orderId
    ) {

        getById(orderId);

        return orderItemRepository.findByOrderId(
                orderId
        );
    }
}