package com.FitnessApp.Controller;

import com.FitnessApp.Security.Model.User;
import com.FitnessApp.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService uService;

    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable long id){
        final Optional<User> user = uService.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user.get());
    }

}
