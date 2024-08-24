package ma.car.ventesvoiture.service;

import lombok.AllArgsConstructor;
import ma.car.ventesvoiture.entity.AppRole;
import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.repository.RoleRepository;
import ma.car.ventesvoiture.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private UserRepository appUserRepository ;
    private PasswordEncoder passwordEncoder;
    private RoleRepository appRoleRepository ;
    @Override
    public Users addNewUser (String username, String password, String email, String confirmPassword) {
        Optional<Users> appUser=appUserRepository.findByUsername (username);
       // if(appUser!=null) throw new RuntimeException("this user already exist");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords not match");
        Users user=Users.builder()
                //.id(Long.valueOf(UUID.randomUUID().toString()))
                .username (username)
                .password(passwordEncoder.encode (password))
                .email(email)
                .build();
        Users savedAppUser = appUserRepository.save(user);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole ;
        // Assuming you have a method findByRole in your repository
       // if (appRole != null) throw new RuntimeException("This role already exists");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        Optional<Users> appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRole(role); // Assuming you have a method findByRole in your repository
        if (appUser.isPresent() && appRole != null) {
            appUser.get().getRoles().add(appRole);
        }
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        Optional<Users> appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRole(role); // Assuming you have a method findByRole in your repository
        if (appUser.isPresent() && appRole != null) {
            appUser.get().getRoles().remove(appRole);
        }
    }
    public Optional<Users> loadUserByUsername (String username) {
        return appUserRepository.findByUsername (username);
    }
}
