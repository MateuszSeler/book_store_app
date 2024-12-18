package app.repository;

import app.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    public Specification<T> build(BookSearchParametersDto bookSearchParametersDto);
}
