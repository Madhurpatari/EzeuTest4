package com.Ezeu4.ezeu4.Controller;

import com.Ezeu4.ezeu4.Entity.User;
import com.Ezeu4.ezeu4.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/users")
public class ApiController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not exist with id :" + id));
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userReq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not exist with id :" + id));

        user.setName(userReq.getName());
        user.setAddress(userReq.getAddress());
        user.setPhoneNumber(userReq.getPhoneNumber());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not exist with id :" + id));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/{deleteReq}")
    public User deleteAttributes(@PathVariable Long id, @PathVariable String deleteReq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (deleteReq.equalsIgnoreCase("address")) {
            user.setAddress(null);
        } else if (deleteReq.equalsIgnoreCase("name")) {
            user.setName(null);
        } else if (deleteReq.equalsIgnoreCase("phoneNumber")) {
            user.setPhoneNumber(null);
        }
        return userRepository.save(user);
    }
}
