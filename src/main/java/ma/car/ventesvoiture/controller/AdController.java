package ma.car.ventesvoiture.controller;

import ma.car.ventesvoiture.dto.AdRequest;
import ma.car.ventesvoiture.entity.Ad;
import ma.car.ventesvoiture.entity.Category;
import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.service.AdService;
import ma.car.ventesvoiture.service.CategoryService;
import ma.car.ventesvoiture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = "http://localhost:4200")
public class AdController {

    @Autowired
    private AdService adService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("allAds")
    public List<Ad> getAllAds() {
        return adService.findAll();
    }
    @GetMapping("/user/{userId}")
    public List<Ad> getAdsByUser(@PathVariable Long userId) {
        return adService.findAdsByUserId(userId);
    }
    @GetMapping("/{id}")
    public Optional<Ad> getAdById(@PathVariable Long id) {
        return adService.findById(id);
    }
    @PostMapping(consumes = {"multipart/form-data"})
    public Ad createAd(@RequestPart("adData") AdRequest adRequest,
                       @RequestPart(value = "image", required = false) MultipartFile image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Cela suppose que le nom d'utilisateur est l'email
        Users user = userService.findByEmail(userEmail);
        Ad ad = new Ad();
       // Optional<Users> user = Optional.of(new Users());
       // user = userService.findById(1L);
        Category category = categoryService.findByName(adRequest.getCategoryName());
        ad.setTitle(adRequest.getTitle());
        ad.setCarburant(adRequest.getCarburant());
        ad.setDescription(adRequest.getDescription());
        ad.setPrice(adRequest.getPrice());
        ad.setBoiteVitesse(adRequest.getBoiteVitesse());
        ad.setCategory(category);
        ad.setPostedDate(adRequest.getPostedDate());
        ad.setVille(adRequest.getVille());
        ad.setBrand(adRequest.getBrand());
        ad.setModel(adRequest.getModel());
        ad.setYear(adRequest.getYear());
        ad.setUser(user);
        if (image != null && !image.isEmpty()) {
            try {
                ad.setImage(image.getBytes()); // Assumons que vous stockez l'image sous forme de BLOB
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image", e);
            }
        }

        adService.save(ad) ;
        return ad;
    }
    @GetMapping("/ads/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        Ad ad = adService.findById(id).orElseThrow(() -> new RuntimeException("Ad not found"));
        byte[] image = ad.getImage(); // Assurez-vous que l'image est stockée sous forme de byte[]

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Utilisez le type MIME approprié selon l'image
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"")
                .body(new ByteArrayResource(image));
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Long id) {
        adService.deleteById(id);
    }

    @GetMapping("/search")
    public List<Ad> searchAds(@RequestParam("q") String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Keyword is missing");
        }
        return adService.searchByTitle(keyword);
    }
}
