package ma.car.ventesvoiture.controller;

import ma.car.ventesvoiture.dto.AdRequest;
import ma.car.ventesvoiture.entity.Ad;
import ma.car.ventesvoiture.entity.Category;
import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.service.AdService;
import ma.car.ventesvoiture.service.CategoryService;
import ma.car.ventesvoiture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    public List<Ad> getAllAds() {
        return adService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Ad> getAdById(@PathVariable Long id) {
        return adService.findById(id);
    }

    @PostMapping
    public Ad createAd(@RequestBody AdRequest adRequest) {
        Ad ad = new Ad();
        Optional<Users> user = Optional.of(new Users());
        user = userService.findById(1L);
        Category category = categoryService.findByName(adRequest.getCategoryName());
        ad.setTitle(adRequest.getTitle());
        ad.setDescription(adRequest.getDescription());
        ad.setPrice(adRequest.getPrice());
        ad.setCategory(category);
        ad.setUser(user.get());
        adService.save(ad) ;
        return ad;
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Long id) {
        adService.deleteById(id);
    }

    // Other endpoints
}
