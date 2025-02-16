package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@Accessors(chain = true)
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
    @DecimalMin("0.00")
    private BigDecimal price;
    private String description;
    private String coverImage;
    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @Column(nullable = false)
    private boolean isDeleted = false;
}
