package app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    private Long id;
    @NotNull
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CartItem> cartItems = new HashSet<>();
}
