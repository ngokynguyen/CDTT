package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.ProductVariant;
import ngokynguyen.example.Service.ProductVariantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productvariants")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductVariantController {

    private final ProductVariantService service;

    public ProductVariantController(
            ProductVariantService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<ProductVariant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductVariant getById(
            @PathVariable("id") Integer id
    ) {
        return service.getById(id);
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariant> getByProduct(
            @PathVariable("productId") Integer productId
    ) {
        return service.getByProduct(productId);
    }

    @PostMapping
    public ProductVariant create(
            @RequestBody ProductVariant variant
    ) {
        return service.create(variant);
    }

    @PutMapping("/{id}")
    public ProductVariant update(
            @PathVariable("id") Integer id,
            @RequestBody ProductVariant variant
    ) {
        return service.update(id, variant);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") Integer id
    ) {
        service.delete(id);

        return "Đã xóa Variant";
    }

}