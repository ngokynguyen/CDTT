package ngokynguyen.example.Controller;

import ngokynguyen.example.Entity.Contact;
import ngokynguyen.example.Repository.ContactRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    private final ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Contact create(
            @RequestBody Contact contact) {

        contact.setStatus(0);

        return repository.save(contact);
    }

    @GetMapping
    public List<Contact> getAll() {
        return repository.findAll();
    }
}