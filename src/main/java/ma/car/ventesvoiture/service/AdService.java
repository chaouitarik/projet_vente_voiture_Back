package ma.car.ventesvoiture.service;


import ma.car.ventesvoiture.entity.Ad;
import ma.car.ventesvoiture.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public List<Ad> findAdsByUserId(Long userId) {
        return adRepository.findByUserId(userId);
    }

    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

    public void deleteById(Long id) {
        adRepository.deleteById(id);
    }
    public List<Ad> searchByTitle(String query) {
        return adRepository.findByTitleContainingIgnoreCase(query);
    }

    // Other business logic methods
}
