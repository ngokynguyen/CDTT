package ngokynguyen.example.Repository;

import ngokynguyen.example.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByUserId(Integer userId);

    List<Address> findByUserIdAndActive(
            Integer userId,
            Integer active
    );
}