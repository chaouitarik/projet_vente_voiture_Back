package ma.car.ventesvoiture.service;


import ma.car.ventesvoiture.entity.Ad;
import ma.car.ventesvoiture.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdService {

    @Autowired
    private AdRepository adRepository;

    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    public List<Ad> findByCategoryId(Long categoryId) {
        return adRepository.findByCategoryId(categoryId);
    }

    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }

    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

    public void deleteById(Long id) {
        adRepository.deleteById(id);
    }

    // Other business logic methods
}
