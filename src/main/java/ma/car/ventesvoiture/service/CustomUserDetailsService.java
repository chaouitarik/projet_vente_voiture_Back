package ma.car.ventesvoiture.service;


import ma.car.ventesvoiture.dto.UserPrincipal;
import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Rechercher l'utilisateur par son email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Affichage des informations de débogage (à supprimer en production)
        System.out.println("User email: " + user.getEmail());
        System.out.println("User password: " + user.getPassword());

        String[] roles =user.getRoles().stream().map(u -> u.getRole()).toArray (String[]::new);
        // Retourner un objet UserDetails contenant les informations de l'utilisateur
        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}