package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}


// jpaRepository
//JPA Repository** is a Spring Data JPA interface that lets you interact with
// a database without writing boilerplate DAO/CRUD code.
//
// Core idea
//Instead of writing SQL or JPA/Hibernate queries by hand,
// you just declare an interface, and Spring Data JPA generates the implementation for you at runtime.
//
//java
//public interface UserRepository extends JpaRepository<User, Long> {
//}
//```
//
//Here:
//        - `User` — the entity type
//- `Long` — the type of the entity's primary key
//
//That's it. You now get a full set of CRUD methods for free.
//
//        ## What you get out of the box
//
//`JpaRepository` extends `PagingAndSortingRepository`, which extends `CrudRepository`. So you inherit methods like:
//
//        ```java
//save(entity)          // insert or update
//findById(id)           // fetch by primary key
//findAll()               // fetch all rows
//deleteById(id)         // delete by id
//count()                 // row count
//existsById(id)          // check existence
//```
//
//Plus pagination and sorting:
//
//        ```java
//Page<User> findAll(Pageable pageable);
//List<User> findAll(Sort sort);
//```
//
//        ## Custom queries — the powerful part
//
//You can define custom finder methods just by naming them correctly, and Spring parses the method name into a query:
//
//        ```java
//List<User> findByEmail(String email);
//List<User> findByNameContainingIgnoreCase(String name);
//List<User> findByAgeGreaterThan(int age);
//List<User> findByStatusAndCity(String status, String city);
//```
//
//No implementation needed — Spring Data JPA reads the method name (`findBy...And...GreaterThan` etc.) and builds the SQL/JPQL automatically.
//
//For anything more complex, you can write your own JPQL or native SQL:
//
//        ```java
//@Query("SELECT u FROM User u WHERE u.email = :email")
//User findUserByEmail(@Param("email") String email);
//
//@Query(value = "SELECT * FROM users WHERE city = :city", nativeQuery = true)
//List<User> findByCityNative(@Param("city") String city);
//```
//
//        ## Why it matters
//
//- Eliminates repetitive DAO boilerplate
//- Works seamlessly with Hibernate underneath (JPA is the spec, Hibernate is the common implementation)
//- Integrates cleanly with `@Transactional`, pagination, sorting, and Spring's dependency injection
//
//        ## Typical layered usage
//
//```java
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findByEmail(String email);
//}
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public User getUser(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//}
//```

