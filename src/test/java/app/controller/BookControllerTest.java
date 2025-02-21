package app.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sqlqueries/insert-dziady-into-books.sql",
        "classpath:sqlqueries/insert-lalka-into-books.sql",
        "classpath:sqlqueries/insert-solaris-into-books.sql",
        "classpath:sqlqueries/insert-wesele-into-books.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sqlqueries/clear-books.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void findById_byExistingId_success() throws Exception {
        Long bookId = 1L;
        BookDto expected = getDziadyDto();

        MvcResult mvcResult = mockMvc.perform(
                        get("/books/" + bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllBooks_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );

        Assertions.assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 4);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void search_byPrice_gettingLalka() throws Exception {
        BigDecimal price = BigDecimal.valueOf(55);

        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("price", price.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );

        assertTrue(actual
                .stream()
                .allMatch(book -> book.getPrice().equals(price)));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void search_byIsbn_gettingSolaris() throws Exception {
        String isbn = "9788838929106";

        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("isbn", isbn)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );

        assertTrue(actual
                .stream()
                .allMatch(book -> book.getIsbn().equals(isbn)));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void search_byTitle_gettingDziady() throws Exception {
        String title = "Dziady";

        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("title", title)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );

        assertTrue(actual
                .stream()
                .allMatch(book -> book.getTitle().equals(title)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createProduct_validRequestDto_success() throws Exception {
        BookCreateRequestDto bookCreateRequestDto = getParadiseCreateRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(bookCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);

        BookDto expected = getParadiseDto();

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void update_validRequestDto_success() throws Exception {
        Long bookId = 1L;
        BookCreateRequestDto bookCreateRequestDto = getParadiseCreateRequestDto();
        BookDto expected = getParadiseDto();

        String jsonRequest = objectMapper.writeValueAsString(bookCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        put("/books/" + bookId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void delete_byExistingId_success() throws Exception {
        Long bookId = 1L;
        MvcResult mvcResult = mockMvc.perform(
                       MockMvcRequestBuilders.delete("/books/" + bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private BookDto getDziadyDto() {
        return new BookDto()
                .setId(1L)
                .setTitle("Dziady")
                .setAuthor("Adam Mickiewicz")
                .setIsbn("9781911414001")
                .setPrice(BigDecimal.valueOf(35))
                .setDescription("Dziady")
                .setCoverImage("dziadycover.ai");
    }

    private BookDto getParadiseDto() {
        return new BookDto()
                .setId(1L)
                .setTitle("Paradise")
                .setAuthor("Abdulrazak Gurnah")
                .setIsbn("9780241001837")
                .setPrice(BigDecimal.valueOf(74))
                .setDescription("Paradise")
                .setCoverImage("paradise.ai");
    }

    private BookCreateRequestDto getParadiseCreateRequestDto() {
        return new BookCreateRequestDto()
                .setTitle("Paradise")
                .setAuthor("Abdulrazak Gurnah")
                .setIsbn("9780241001837")
                .setPrice(BigDecimal.valueOf(74))
                .setDescription("Paradise")
                .setCoverImage("paradise.ai");
    }
}
