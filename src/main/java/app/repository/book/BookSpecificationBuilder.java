package app.repository.book;

import app.dto.book.BookSearchParametersDto;
import app.model.Book;
import app.repository.SpecificationBuilder;
import app.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameter) {
        Specification<Book> specification = Specification.where(null);

        if (searchParameter.author() != null
                && searchParameter.author().length > 0) {
            specification = specification.and(
                    bookSpecificationProviderManager
                            .getSpecificationProvider("author")
                            .getSpecification(searchParameter.author()));
        }
        if (searchParameter.description() != null
                && searchParameter.description().length > 0) {
            specification = specification.and(
                    bookSpecificationProviderManager
                            .getSpecificationProvider("description")
                            .getSpecification(searchParameter.description()));
        }
        if (searchParameter.isbn() != null
                && searchParameter.isbn().length > 0) {
            specification = specification.and(
                    bookSpecificationProviderManager
                            .getSpecificationProvider("isbn")
                            .getSpecification(searchParameter.isbn()));
        }
        if (searchParameter.price() != null
                && searchParameter.price().length > 0) {
            specification = specification.and(
                    bookSpecificationProviderManager
                            .getSpecificationProvider("price")
                            .getSpecification(searchParameter.price()));
        }
        if (searchParameter.title() != null
                && searchParameter.title().length > 0) {
            specification = specification.and(
                    bookSpecificationProviderManager
                            .getSpecificationProvider("title")
                            .getSpecification(searchParameter.title()));
        }

        return specification;
    }
}
