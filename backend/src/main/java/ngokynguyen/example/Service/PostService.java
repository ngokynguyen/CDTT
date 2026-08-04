package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Post;
import ngokynguyen.example.Entity.User;
import ngokynguyen.example.Repository.PostRepository;
import ngokynguyen.example.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getById(Integer id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy bài viết"
                        ));
    }

    public Post getBySlug(String slug) {

        return postRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy bài viết"
                        ));
    }

    public List<Post> getPosts() {

        return postRepository.findByTypeAndStatus(
                "POST",
                1
        );
    }

    public List<Post> getBanners() {

        return postRepository.findByTypeAndStatus(
                "BANNER",
                1
        );
    }

    public Post create(
            Post post,
            Integer authorId
    ) {

        if (post.getSlug() != null &&
                postRepository.existsBySlug(
                        post.getSlug()
                )) {

            throw new RuntimeException(
                    "Slug đã tồn tại"
            );
        }

        if (authorId != null) {

            User author =
                    userRepository.findById(authorId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Không tìm thấy tác giả"
                                    ));

            post.setAuthor(author);
        }

        return postRepository.save(post);
    }

    public Post update(
            Integer id,
            Post post
    ) {

        Post existing = getById(id);

        existing.setTitle(post.getTitle());
        existing.setSlug(post.getSlug());
        existing.setType(post.getType());
        existing.setImage(post.getImage());
        existing.setContent(post.getContent());
        existing.setSortOrder(post.getSortOrder());
        existing.setStatus(post.getStatus());

        return postRepository.save(existing);
    }

    public void delete(Integer id) {

        Post post = getById(id);

        postRepository.delete(post);
    }
}