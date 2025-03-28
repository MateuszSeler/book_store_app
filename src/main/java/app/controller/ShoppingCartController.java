package app.controller;

import app.dto.cartitem.CartItemCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.service.ShoppingCartService;
import app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "shopping carts manager",
        description = "Endpoints for managing shopping carts in book shop app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "getting shopping cart",
            description = "getting shopping cart")
    public ShoppingCartDto get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return shoppingCartService.get(
                userService.findByEmail(authentication.getName()).getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "adding book",
            description = "adding book by creating new item cart"
                    + "and attaching it to shopping cart connected to the user")
    public ResponseEntity<ShoppingCartDto> add(
            @RequestBody CartItemCreateRequestDto cartItemCreateRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ShoppingCartDto shoppingCartDto = shoppingCartService.addOrUpdateItemCart(
                cartItemCreateRequestDto,
                userService.findByEmail(authentication.getName()).getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{bookId}")
    @Operation(summary = "removing book",
            description = "removing book from shopping cart by book id")
    public ShoppingCartDto delete(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return shoppingCartService.deleteItemCartByBookId(
                bookId, userService.findByEmail(authentication.getName()).getId());
    }

}
