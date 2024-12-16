package app.repository;

import app.dto.BookSearchParameter;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    public Specification<T> build(BookSearchParameter bookSearchParameter);
}
