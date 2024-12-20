package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE books SET is_Deleted = true WHERE id = ?")
@SQLRestriction(value = "is_Deleted = false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
