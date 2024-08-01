package ma.car.ventesvoiture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data@NoArgsConstructor@AllArgsConstructor
public class AdRequest {
    private String title;
    private String description;
    private Double price;
    private String categoryName; // ou Long categoryId
    // getters et setters
}