package ma.car.ventesvoiture.repository;

import ma.car.ventesvoiture.entity.AppRole;
import ma.car.ventesvoiture.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<AppRole, Long> {

    AppRole findByRole(String role);
}
