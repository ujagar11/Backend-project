package com.BackendProject.Shopeeee.controller;

import com.BackendProject.Shopeeee.exception.ResourceNotFoundException;
import com.BackendProject.Shopeeee.model.Cart;
import com.BackendProject.Shopeeee.model.CartItem;
import com.BackendProject.Shopeeee.model.User;
import com.BackendProject.Shopeeee.repository.UserRepository;
import com.BackendProject.Shopeeee.response.ApiResponse;
import com.BackendProject.Shopeeee.service.cart.ICartItemService;
import com.BackendProject.Shopeeee.service.cart.ICartService;
import com.BackendProject.Shopeeee.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam  Long productId,@RequestParam  Integer quantity){

        try{

            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Success", null));

        }catch (ResourceNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage() , null));

        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCArt(@PathVariable Long cartId,@PathVariable Long itemId){
         try {
             cartItemService.removeItemFromCart(cartId, itemId);
             return ResponseEntity.ok(new ApiResponse("Removed",null));
         }catch (ResourceNotFoundException e){

             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
         }

    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,@PathVariable Long itemId,@RequestParam  Integer quantity){
        try{
            cartItemService.updateItemQuantity(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("updated",null));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }


}
