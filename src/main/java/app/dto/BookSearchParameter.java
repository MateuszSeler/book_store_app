package app.dto;

public record BookSearchParameter(
        String[] author,
        String[] description,
        String[] isbn,
        String[] title,
        String[] price) {
}
