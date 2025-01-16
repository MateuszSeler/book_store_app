package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@SQLDelete(sql = "UPDATE books SET is_Deleted = true WHERE id = ?")
@SQLRestriction(value = "is_Deleted = false")
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName roleType;
    @Column(nullable = false)
    private boolean isDeleted = false;

    enum RoleName {
        ADMIN,
        USER;
    }

    @Override
    public String getAuthority() {
        return roleType.toString();
    }
}
