package Maven.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // INCOME or EXPENSE

    private boolean isCustom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Null for default categories, set for custom ones
}