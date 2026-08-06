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
                        new RuntimeException("Không tìm thấy Variant"));
    }

    public List<ProductVariant> getByProduct(Integer productId) {
        return variantRepository.findByProductIdAndStatus(
                productId,
                1
        );
    }

    public ProductVariant create(ProductVariant variant) {

        Integer productId =
                variant.getProduct().getId();

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException("Không tìm thấy sản phẩm"));

        variant.setProduct(product);

        return variantRepository.save(variant);
    }

    public ProductVariant update(
            Integer id,
            ProductVariant data
    ) {

        ProductVariant variant = getById(id);

        variant.setSize(data.getSize());
        variant.setColor(data.getColor());
        variant.setQuantity(data.getQuantity());
        variant.setStatus(data.getStatus());

        if (data.getProduct() != null) {

            Product product =
                    productRepository.findById(
                                    data.getProduct().getId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException("Không tìm thấy sản phẩm"));

            variant.setProduct(product);
        }

        return variantRepository.save(variant);
    }

    public void delete(Integer id) {

        ProductVariant variant = getById(id);

        variantRepository.delete(variant);
    }

}