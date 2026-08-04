package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Post;
import ngokynguyen.example.Repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Post> getPosts() {
        return repository.findByTypeAndStatus(
                "POST",
                1
        );
    }

    @GetMapping("/banners")
    public List<Post> getBanners() {
        return repository.findByTypeAndStatus(
                "BANNER",
                1
        );
    }
}