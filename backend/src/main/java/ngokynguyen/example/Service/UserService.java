package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.User;
import ngokynguyen.example.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy người dùng"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy email"));
    }

    public User create(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        return userRepository.save(user);
    }

    public User update(Integer id, User user) {

        User existing = getById(id);

        existing.setFullName(user.getFullName());
        existing.setPhone(user.getPhone());
        existing.setAvatar(user.getAvatar());
        existing.setStatus(user.getStatus());

        return userRepository.save(existing);
    }

    public void delete(Integer id) {

        User user = getById(id);

        userRepository.delete(user);
    }
}