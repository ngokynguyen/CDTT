package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Contact;
import ngokynguyen.example.Repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(
            ContactRepository contactRepository
    ) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAll() {

        return contactRepository.findAll();
    }

    public Contact getById(Integer id) {

        return contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy liên hệ"
                        ));
    }

    public List<Contact> getByStatus(
            Integer status
    ) {

        return contactRepository.findByStatus(
                status
        );
    }

    public Contact create(Contact contact) {

        contact.setStatus(0);

        return contactRepository.save(contact);
    }

    public Contact updateStatus(
            Integer id,
            Integer status
    ) {

        Contact contact = getById(id);

        contact.setStatus(status);

        return contactRepository.save(contact);
    }

    public void delete(Integer id) {

        Contact contact = getById(id);

        contactRepository.delete(contact);
    }
}