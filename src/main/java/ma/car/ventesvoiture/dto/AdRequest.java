package ma.car.ventesvoiture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data@NoArgsConstructor@AllArgsConstructor
public class AdRequest {
    private String title;
    private String description;
    private Double price;
    private String ville ;
    private LocalDateTime postedDate;
    private String categoryName; // ou Long categoryId
    private String carburant ;
    private String boiteVitesse ;
    private String brand;
    private String model ;
    private String year ;
    // getters et setters
}