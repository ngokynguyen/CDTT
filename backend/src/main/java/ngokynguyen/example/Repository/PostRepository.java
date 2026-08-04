package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Post> findByType(String type);

    List<Post> findByTypeAndStatus(
            String type,
            Integer status
    );

    List<Post> findByStatusOrderBySortOrderAsc(
            Integer status
    );
}