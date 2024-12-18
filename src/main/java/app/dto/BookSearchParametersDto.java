package app.dto;

public record BookSearchParametersDto(
        String[] author,
        String[] description,
        String[] isbn,
        String[] title,
        String[] price) {
}
