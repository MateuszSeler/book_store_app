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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
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
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = "classpath:sqlqueries/insert-dziady-into-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_byExistingId_success() throws Exception {
        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Dziady")
                .setAuthor("Adam Mickiewicz")
                .setIsbn("9781911414001")
                .setPrice(BigDecimal.valueOf(35))
                .setDescription("Dziady")
                .setCoverImage("dziadycover.ai");

        MvcResult mvcResult = mockMvc.perform(
                        get("/books/1")
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
    @Sql(scripts = {"classpath:sqlqueries/insert-dziady-into-books.sql",
            "classpath:sqlqueries/insert-lalka-into-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        assertTrue(actual.size() == 2);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {"classpath:sqlqueries/insert-dziady-into-books.sql",
            "classpath:sqlqueries/insert-lalka-into-books.sql",
            "classpath:sqlqueries/insert-solaris-into-books.sql",
            "classpath:sqlqueries/insert-wesele-into-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = {"classpath:sqlqueries/insert-dziady-into-books.sql",
            "classpath:sqlqueries/insert-lalka-into-books.sql",
            "classpath:sqlqueries/insert-solaris-into-books.sql",
            "classpath:sqlqueries/insert-wesele-into-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = {"classpath:sqlqueries/insert-dziady-into-books.sql",
            "classpath:sqlqueries/insert-lalka-into-books.sql",
            "classpath:sqlqueries/insert-solaris-into-books.sql",
            "classpath:sqlqueries/insert-wesele-into-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void search_byTitle_gettingDziady() throws Exception {
        String title = "Dziady";

        BookDto expectedBook = new BookDto()
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

        List<BookDto> expected = List.of(expectedBook);

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
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createProduct_validRequestDto_success() throws Exception {
        BookCreateRequestDto bookCreateRequestDto = new BookCreateRequestDto()
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

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

        BookDto expected = new BookDto()
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = "classpath:sqlqueries/insert-dziady-into-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_validRequestDto_success() throws Exception {

        BookCreateRequestDto bookCreateRequestDto = new BookCreateRequestDto()
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

        String jsonRequest = objectMapper.writeValueAsString(bookCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        put("/books/1")
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
    @Sql(scripts = "classpath:sqlqueries/insert-dziady-into-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sqlqueries/clear-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_byExistingId_success() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                       MockMvcRequestBuilders.delete("/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
