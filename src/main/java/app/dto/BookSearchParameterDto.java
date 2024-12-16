package app.dto;

public record BookSearchParameterDto(
        String[] author,
        String[] description,
        String[] isbn,
        String[] title,
        String[] price) {
}
