package com.EcommerceProject.Service;

import com.EcommerceProject.Exceptions.APIException;
import com.EcommerceProject.Exceptions.ResourceNotFoundException;
import com.EcommerceProject.Model.Cart;
import com.EcommerceProject.Model.CartItem;
import com.EcommerceProject.Model.Product;
import com.EcommerceProject.Payload.CartDTO;
import com.EcommerceProject.Payload.ProductDTO;
import com.EcommerceProject.Repositories.CartItemRepository;
import com.EcommerceProject.Repositories.CartRepository;
import com.EcommerceProject.Repositories.ProductRepository;
import com.EcommerceProject.Util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // Find existing or create one
        Cart cart = createCart();

        // Retrieve the Product Details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        // Perform Validation
        CartItem cartItem = cartItemRepository.
                findCartIemByProductIdAndCartId(cart.getCartId(), productId);

        if (cartItem != null) {
            throw new APIException("Product "+ product.getProductName()+" already exists in Cart");
        }

       if (product.getQuantity() == 0){
           throw new APIException(product.getProductName()+"is not available in Cart");
       }

       if (product.getQuantity() < quantity){
            throw new APIException("Please , make an order of the "+product.getProductName()+
                    "less than or equal to the quantity"+ product.getQuantity()+".");
       }


        // Create Cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getPrice());
        // Save Cart Item
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() +(product.getSpecialPrice()*quantity));
        cartRepository.save(cart);
        CartDTO cartDTO =modelMapper.map(cart,CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream =cartItems.stream().map(item ->{
              ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class);
              map.setQuantity(item.getQuantity());
              return map;
                });
        cartDTO.setProducts(productStream.toList());

        // Return updated Cart
        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;


    }
}
