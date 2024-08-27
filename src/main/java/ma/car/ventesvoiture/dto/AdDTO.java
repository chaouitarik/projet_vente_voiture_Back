package ma.car.ventesvoiture.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String ville;
    private String carburant;
    private String boiteVitesse;
    private String brand;
    private Double km;
    private String model;
    private String year;
    private LocalDateTime postedDate;
    private String username; // Remplacer l'objet Users par juste le nom d'utilisateur
    private String categoryName; // Remplacer l'objet Category par juste le nom de la catégorie
    private List<Long> imageIds; // Liste des IDs des images associées à l'annonce
}
