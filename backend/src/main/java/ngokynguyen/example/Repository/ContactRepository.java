package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository
        extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(Integer status);

    List<Contact> findByEmail(String email);
}