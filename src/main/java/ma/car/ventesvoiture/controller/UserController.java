package ma.car.ventesvoiture.controller;


import ma.car.ventesvoiture.entity.Users;
import ma.car.ventesvoiture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
   /* @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }*/

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
