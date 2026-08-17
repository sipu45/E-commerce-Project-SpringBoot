package com.EcommerceProject.Exceptions;


import com.EcommerceProject.Payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)  // Generic Exception Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fieldName = ((FieldError)err).getField();
            String  message = err.getDefaultMessage();
            response.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}


// Exception handling in Spring Boot means catching and managing errors that occur
// during request processing (bad input, missing resources, database errors, etc.) — instead of letting
// them crash the app or return an ugly, unhelpful default error page/stack trace to the client,
// you handle them and return clean, meaningful, consistent responses.
//
// Why it matters
//
//Without proper exception handling, if something goes wrong, Spring Boot returns a generic error like:
//```json
//{
//  "timestamp": "2026-08-17T10:00:00.000+00:00",
//  "status": 500,
//  "error": "Internal Server Error",
//  "path": "/api/products/999"
//}
//```
//This leaks internal details and isn't useful for a frontend to work with.
// Proper exception handling gives you control over the exact status code, message, and structure returned.
//
// The standard approach: `@ControllerAdvice` + `@ExceptionHandler`
//
//This is the most common, production-grade pattern —
// a centralized place that catches exceptions thrown anywhere in your controllers,
// instead of writing try-catch blocks in every single method.
//
//Step 1 — Create a custom exception:
//
//```java
//public class ResourceNotFoundException extends RuntimeException {
//    public ResourceNotFoundException(String message) {
//        super(message);
//    }
//}
//```
//
//Step 2 — Throw it wherever needed (e.g., in your service layer):
//
//```java
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public Product getProductById(Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
//    }
//}
//```
//
//Step 3 — Create a global exception handler:
//
//```java
//@RestControllerAdvice   // combines @ControllerAdvice + @ResponseBody
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
//        ErrorResponse error = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                ex.getMessage(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class) // for @Valid validation failures
//    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
//        String message = ex.getBindingResult().getFieldErrors().stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class) // catch-all fallback for anything unhandled
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        ErrorResponse error = new ErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Something went wrong. Please try again later.",
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
//```
//
//**Step 4 — A simple, consistent error response shape:**
//
//```java
//public class ErrorResponse {
//    private int status;
//    private String message;
//    private LocalDateTime timestamp;
//
//    // constructor, getters, setters
//}
//```
//
//**Result — now instead of a stack trace, the client gets:**
//```json
//{
//  "status": 404,
//  "message": "Product not found with id: 999",
//  "timestamp": "2026-08-17T10:00:00"
//}
//```
//
//## How `@RestControllerAdvice` actually works
//
//- `@ControllerAdvice` marks a class as a **global handler** that intercepts exceptions thrown from *any* `@Controller`/`@RestController` in your app — you don't attach it to individual controllers.
//- `@ExceptionHandler(SomeException.class)` on a method inside it says "when this specific exception type is thrown anywhere, run this method to build the response."
//- Spring matches the **most specific exception type first** — so a handler for `ResourceNotFoundException` takes priority over the generic `Exception.class` fallback.
//
//## Common exception types you'll handle in a real project
//
//| Exception | When it happens | Typical HTTP status |
//|---|---|---|
//| `ResourceNotFoundException` (custom) | Requested entity doesn't exist | 404 |
//| `MethodArgumentNotValidException` | `@Valid` validation fails on request body | 400 |
//| `HttpMessageNotReadableException` | Malformed JSON in request | 400 |
//| `DataIntegrityViolationException` | DB constraint violated (e.g., duplicate unique field) | 409 |
//| `AccessDeniedException` | User lacks permission (Spring Security) | 403 |
//| `BadCredentialsException` | Wrong login credentials | 401 |
//
//## Where this fits your projects
//
//In your EcommerceProject, you'd use this pattern for things like: throwing `ResourceNotFoundException` when a product/order ID doesn't exist, catching validation errors on signup/checkout forms, and handling JWT-related auth exceptions from Spring Security — all funneling through one `GlobalExceptionHandler` instead of scattering try-catch blocks across every controller method. It's also a strong thing to mention in interviews since it shows you think about clean API design, not just "making it work."