package com.acopl.microservice_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.service.UserService;



@RestController
//contiene la url
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.findall();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);

    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User newuser){
        User user = userService.save(newuser);
        //devuelve el titulo con el status de creado con el cuerpo del uuser creado
        return ResponseEntity.status(HttpStatus.CREATED).body(newuser);
    }

    //  el cliente esta ingresando un id en la url y nosotros tenemos que responde con la
    // informacion del id que tenemos guardado
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            // responde con el Establecimiento
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // retorna que no se encontró y no tiene cuerpo
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        try {
            userService.deleteById(id);
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/authenticate-id") //valiidar con nitchen
    public ResponseEntity<String> authenticateById(
            @RequestParam Long id,
            @RequestParam String email,
            @RequestParam String rol) {

        boolean valid = userService.authenticateById(id, email, rol);

        if (valid) {
            return ResponseEntity.ok("ID authentication successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
