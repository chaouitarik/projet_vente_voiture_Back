package ma.car.ventesvoiture.service;



import ma.car.ventesvoiture.entity.Category;
import ma.car.ventesvoiture.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

}
