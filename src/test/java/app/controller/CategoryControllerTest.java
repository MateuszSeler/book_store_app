package app.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.dto.book.BookDto;
import app.dto.category.CategoryCreateRequestDto;
import app.dto.category.CategoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Sql(scripts = {"classpath:sqlqueries/insert-polish_literature-into-categories.sql",
        "classpath:sqlqueries/insert-foreign_literature-into-categories.sql",
        "classpath:sqlqueries/insert-dziady-into-books.sql",
        "classpath:sqlqueries/insert-lalka-into-books.sql",
        "classpath:sqlqueries/insert-solaris-into-books.sql",
        "classpath:sqlqueries/insert-books_categories-into-books_categories.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:sqlqueries/clear-books.sql",
        "classpath:sqlqueries/clear-categories.sql",
        "classpath:sqlqueries/clear-books_categories.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CategoryControllerTest {
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
    void getById_byExistingId_success() throws Exception {
        Long categoryId = 1L;
        CategoryDto expected = getPoetryDto();

        MvcResult mvcResult = mockMvc.perform(
                        get("/categories/" + categoryId)
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
    void getAll_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<CategoryDto>>() {}
        );

        Assertions.assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 2);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void findAllByCategoryId_byExistingId_gettingLalka() throws Exception {
        Long categoryId = 1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/categories/bycategoryid/" + categoryId)
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
        assertTrue(actual.size() == 1);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void create_validRequestDto_success() throws Exception {
        CategoryCreateRequestDto categoryCreateRequestDto = getPoetryCreateRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(categoryCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        CategoryDto expected = getPoetryDto();

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void update_validRequestDto_success() throws Exception {
        Long categoryId = 2L;
        CategoryCreateRequestDto categoryCreateRequestDto = getPoetryCreateRequestDto();
        CategoryDto expected = getPoetryDto();

        String jsonRequest = objectMapper.writeValueAsString(categoryCreateRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                        put("/categories/" + categoryId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void delete() throws Exception {
        Long categoryId = 2L;

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/categories/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private CategoryDto getPoetryDto() {
        return new CategoryDto()
                .setName("Poetry")
                .setDescription("Poetry");
    }

    private CategoryCreateRequestDto getPoetryCreateRequestDto() {
        return new CategoryCreateRequestDto()
                .setName("Poetry")
                .setDescription("Poetry");
    }
}
