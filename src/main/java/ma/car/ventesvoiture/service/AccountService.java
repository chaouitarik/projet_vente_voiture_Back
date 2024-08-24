package ma.car.ventesvoiture.service;

import ma.car.ventesvoiture.entity.AppRole;
import ma.car.ventesvoiture.entity.Users;

import java.util.Optional;

public interface AccountService {
    Users addNewUser(String username, String password, String email, String confirmPassword);

    AppRole addNewRole(String role);

    void addRoleToUser(String username, String role);

    void removeRoleFromUser(String username, String role);
    Optional<Users> loadUserByUsername(String username);
}