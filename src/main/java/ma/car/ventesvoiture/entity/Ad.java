package ma.car.ventesvoiture.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    //@Lob
    //private byte[] image;
    @CreationTimestamp
    private LocalDateTime postedDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
   // @Column(nullable = false)
    private String phonenumber ;
    @JsonIgnore
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();



    public void addImage(byte[] imageData) {
        Image image = new Image();
        image.setData(imageData);
        image.setAd(this);
        this.images.add(image);
    }

}
