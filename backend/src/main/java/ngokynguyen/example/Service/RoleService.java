package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Role;
import ngokynguyen.example.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền"));
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role update(Integer id, Role role) {
        Role existing = getById(id);

        existing.setName(role.getName());

        return roleRepository.save(existing);
    }

    public void delete(Integer id) {
        Role role = getById(id);
        roleRepository.delete(role);
    }
}