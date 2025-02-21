package app.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.dto.cartitem.CartItemCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.dto.user.UserLoginRequestDto;
import app.dto.user.UserLoginResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sqlqueries/insert-jan-into-users.sql",
        "classpath:sqlqueries/make-jan-user.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"classpath:sqlqueries/insert-cart_item_with_dziady-into-cart_items.sql",
        "classpath:sqlqueries/insert-dziady-into-books.sql",
        "classpath:sqlqueries/insert-jans_shopping_cart-into-shopping_carts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:sqlqueries/clear-books.sql",
        "classpath:sqlqueries/clear-shopping_carts.sql",
        "classpath:sqlqueries/clear-cart_items.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void get_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/shoppingcart")
                                .header("Authorization", "Bearer " + loginUser().token())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ShoppingCartDto.class);

        Assertions.assertNotNull(actual);
    }

    @Test
    void add() throws Exception {
        CartItemCreateRequestDto cartItemCreateRequestDto = getCartItemCreateRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(cartItemCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/shoppingcart")
                                .header("Authorization", "Bearer " + loginUser().token())
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        ShoppingCartDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ShoppingCartDto.class);

        assertTrue(actual.getUserId() == 1);
        assertTrue(actual.getCartItemDtos().size() == 1);
        assertTrue(actual.getCartItemDtos()
                .stream()
                .allMatch(c -> c.getBookDto().getId().equals(1L)));
    }

    @Test
    void delete_byExistingId_success() throws Exception {
        Long cartItemId = 1L;

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shoppingcart/" + cartItemId)
                                .header("Authorization", "Bearer " + loginUser().token())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    private UserLoginResponseDto loginUser() throws Exception {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto()
                .setEmail("jan@gmail.com")
                .setPassword("haslojana");

        String jsonRequest = objectMapper.writeValueAsString(userLoginRequestDto);

        MvcResult mvcLoginResult = mockMvc.perform(
                        post("/authentication/login")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                mvcLoginResult.getResponse().getContentAsString(), UserLoginResponseDto.class);
    }

    private CartItemCreateRequestDto getCartItemCreateRequestDto() {
        return new CartItemCreateRequestDto()
                .setBookId(1L)
                .setQuantity(1);
    }
}
