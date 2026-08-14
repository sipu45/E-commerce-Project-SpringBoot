package com.EcommerceProject.Config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
}


// Model mapper example :
//ModelMapper is a Java library that automatically maps fields from one object to another —
// most commonly used to convert between Entity classes (your database/JPA model)
// and DTO classes (what you actually send/receive via your API),
// without writing manual getter/setter copying code for every field.
//
//        **Why you need this at all:**
//In a typical Spring Boot app,
// you don't want to expose your JPA `Entity` directly in API responses (bad practice — leaks DB structure, causes lazy-loading issues, security risks).
// So you create a separate `DTO` (Data Transfer Object) with just the fields the client needs. But converting Entity → DTO manually is repetitive:
//
//
//// Manual way — tedious, and gets worse as fields grow
//UserDTO dto = new UserDTO();
//dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setAge(user.getAge());
//// ...repeat for every field, every entity
//        ```
//
//ModelMapper eliminates this boilerplate.
//
//**Setup — add the dependency:**
//        ```xml
//        <dependency>
//    <groupId>org.modelmapper</groupId>
//    <artifactId>modelmapper</artifactId>
//    <version>3.2.0</version>
//</dependency>
//        ```
//
//        **Basic example:**
//
//        ```java
//// Entity
//public class User {
//    private Long id;
//    private String name;
//    private String email;
//    private String password; // sensitive - shouldn't leave the backend
//    // getters, setters, constructors
//}
//
//// DTO
//public class UserDTO {
//    private Long id;
//    private String name;
//    private String email;
//    // no password field - only expose what's safe
//    // getters, setters
//}
//```
//
//        ```java
//import org.modelmapper.ModelMapper;
//
//public class UserService {
//
//    private final ModelMapper modelMapper = new ModelMapper();
//
//    public UserDTO convertToDTO(User user) {
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    public User convertToEntity(UserDTO userDTO) {
//        return modelMapper.map(userDTO, User.class);
//    }
//}
//```
//
//ModelMapper works by matching field names between the source and destination objects (using reflection internally) —
// since `name`, `email`, and `id` exist on both `User` and `UserDTO`,
// it copies them automatically. `password` simply gets ignored since `UserDTO` doesn't have that field.
//
//    Using it properly in a Spring Boot `@Service` (typically as a `@Bean`):**
//
//        ```java
//@Configuration
//public class AppConfig {
//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
//}
//```
//
//        ```java
//@Service
//public class UserService {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserDTO getUser(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return modelMapper.map(user, UserDTO.class);
//    }
//}
//```
//
//        **Handling mismatched field names** (e.g., DTO field named differently from Entity field):
//
//        ```java
//modelMapper.typeMap(User.class, UserDTO.class)
//    .addMapping(User::getEmail, UserDTO::setContactEmail);
//```
//

