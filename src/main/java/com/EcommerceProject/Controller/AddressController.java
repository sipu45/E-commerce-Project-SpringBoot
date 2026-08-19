package com.EcommerceProject.Controller;

import com.EcommerceProject.Model.User;
import com.EcommerceProject.Payload.AddressDTO;
import com.EcommerceProject.Service.AddressService;
import com.EcommerceProject.Util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);

    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddress() {
        List<AddressDTO> addressList = addressService.getAddress();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
      AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddress() {
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getUserAddress(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId ,
                                                        @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedaddressDTO = addressService.updateAddress(addressId,addressDTO);
        return new ResponseEntity<>(updatedaddressDTO, HttpStatus.OK);
    }


    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}


//**DTO** stands for **Data Transfer Object**.
// It's a design pattern used to carry data between different layers or components of an application —
// most commonly between the backend and frontend, or between the service layer and the controller layer in a Java/Spring app.
//
//**Why use a DTO instead of exposing your entity directly?**
//
//1. Decoupling** — Your database entity (`@Entity`) can change without breaking the API contract exposed to clients.
//2. Security** — You avoid accidentally exposing sensitive fields (like `password`, internal IDs, or audit fields) in API responses.
//3. Avoiding lazy-loading issues** — In JPA, returning entities directly (especially with `@OneToMany`/`@ManyToOne` relationships) can trigger `LazyInitializationException` or accidentally serialize huge object graphs. DTOs let you control exactly what's sent.
//4. **Tailored shape** — A DTO can combine or reshape data from multiple entities to match exactly what the client needs (e.g., a `UserSummaryDTO` with just `name` and `email`, instead of the full `User` entity).
//
//**Example in a Spring Boot app:**
//
//```java
/// / Entity (maps to DB table)
//@Entity
//public class User {
//    @Id
//    private Long id;
//    private String name;
//    private String email;
//    private String password;   // sensitive — should NOT be exposed
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;
//}
//
//// DTO (what you actually send to the client)
//public class UserDTO {
//    private Long id;
//    private String name;
//    private String email;
//
//    // constructor, getters/setters
//    public UserDTO(User user) {
//        this.id = user.getId();
//        this.name = user.getName();
//        this.email = user.getEmail();
//    }
//}
//```
//
//Then in your controller/service, you convert the entity to a DTO before returning it:
//
//```java
//@GetMapping("/users/{id}")
//public UserDTO getUser(@PathVariable Long id) {
//    User user = userRepository.findById(id).orElseThrow();
//    return new UserDTO(user); // only exposes safe fields
//}
//```
//
//You'll often see this paired with a **mapper** (manual, or using a library like **MapStruct**) to handle entity ↔ DTO conversion cleanly, especially as your project grows.
//
//If you're working on this for a specific project (like your e-commerce app), I can show how to structure DTOs for request vs. response (e.g., `CreateOrderRequestDTO` vs `OrderResponseDTO`) — that's a common real-world pattern.