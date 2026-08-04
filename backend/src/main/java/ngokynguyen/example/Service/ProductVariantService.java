package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Product;
import ngokynguyen.example.Entity.ProductVariant;
import ngokynguyen.example.Repository.ProductRepository;
import ngokynguyen.example.Repository.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    public ProductVariantService(
            ProductVariantRepository variantRepository,
            ProductRepository productRepository
    ) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    public List<ProductVariant> getAll() {
        return variantRepository.findAll();
    }

    public ProductVariant getById(Integer id) {

        return variantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy biến thể sản phẩm"
                        ));
    }

    public List<ProductVariant> getByProductId(
            Integer productId
    ) {

        return variantRepository.findByProductId(productId);
    }

    public List<ProductVariant> getActiveByProductId(
            Integer productId
    ) {

        return variantRepository.findByProductIdAndStatus(
                productId,
                1
        );
    }

    public ProductVariant create(
            Integer productId,
            ProductVariant variant
    ) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy sản phẩm"
                                ));

        variant.setProduct(product);

        return variantRepository.save(variant);
    }

    public ProductVariant update(
            Integer id,
            ProductVariant variant
    ) {

        ProductVariant existing = getById(id);

        existing.setSize(variant.getSize());
        existing.setColor(variant.getColor());
        existing.setQuantity(variant.getQuantity());
        existing.setStatus(variant.getStatus());

        return variantRepository.save(existing);
    }

    public void delete(Integer id) {

        ProductVariant variant = getById(id);

        variantRepository.delete(variant);
    }

    public void decreaseQuantity(
            Integer variantId,
            Integer quantity
    ) {

        ProductVariant variant =
                getById(variantId);

        if (variant.getQuantity() < quantity) {

            throw new RuntimeException(
                    "Sản phẩm không đủ số lượng"
            );
        }

        variant.setQuantity(
                variant.getQuantity() - quantity
        );

        variantRepository.save(variant);
    }
}