package ma.car.ventesvoiture.entity;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;
    private String ville ;
    private String carburant ;
    private String boiteVitesse ;
    private String brand;
    private String model ;
    private String year ;
    @Lob
    private byte[] image;
    @CreationTimestamp
    private LocalDateTime postedDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
