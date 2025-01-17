package app.dto.book;

public record BookSearchParametersDto(
        String[] author,
        String[] description,
        String[] isbn,
        String[] title,
        String[] price,
        String[] category) {
}
