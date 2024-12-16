package app.repository.book.specifications;

import app.model.Book;
import app.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DescriptionSpecificationProvider implements SpecificationProvider<Book> {
    public static final String SEARCHED_FIELD = "description";

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get("description").in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return SEARCHED_FIELD;
    }
}
