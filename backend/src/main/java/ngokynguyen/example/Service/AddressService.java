package ngokynguyen.example.Service;

import ngokynguyen.example.Entity.Address;
import ngokynguyen.example.Entity.User;
import ngokynguyen.example.Repository.AddressRepository;
import ngokynguyen.example.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<Address> getByUserId(Integer userId) {

        return addressRepository.findByUserId(userId);
    }

    public Address getById(Integer id) {

        return addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy địa chỉ"
                        ));
    }

    public Address create(
            Integer userId,
            Address address
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy người dùng"
                                ));

        address.setUser(user);

        return addressRepository.save(address);
    }

    public Address update(
            Integer id,
            Address address
    ) {

        Address existing = getById(id);

        existing.setReceiverName(
                address.getReceiverName()
        );

        existing.setPhone(
                address.getPhone()
        );

        existing.setAddress(
                address.getAddress()
        );

        existing.setActive(
                address.getActive()
        );

        return addressRepository.save(existing);
    }

    public void delete(Integer id) {

        Address address = getById(id);

        addressRepository.delete(address);
    }

    public void setActive(
            Integer userId,
            Integer addressId
    ) {

        List<Address> addresses =
                addressRepository.findByUserId(userId);

        for (Address address : addresses) {

            address.setActive(
                    address.getId().equals(addressId)
                            ? 1
                            : 0
            );

            addressRepository.save(address);
        }
    }
}