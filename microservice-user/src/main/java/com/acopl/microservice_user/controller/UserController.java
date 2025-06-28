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

import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.dto.UserDTO;
import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.service.UserService;



@RestController
//contiene la url
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<UserDTO> users = userService.findall();
        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(users);
    }

    //  el cliente esta ingresando un id en la url y nosotros tenemos que responde con la
    // informacion del id que tenemos guardado
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        try {
            UserDTO user = userService.findById(id);
            // responde con el Establecimiento
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // retorna que no se encontró y no tiene cuerpo
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user){
        
        UserDTO newUser = userService.saveUser(user);
        //devuelve el titulo con el status de creado con el cuerpo del uuser creado
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        try {
            UserDTO userToUpdate = userService.findById(id);

            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setRol(updatedUser.getRol());

            userService.saveUser(userToUpdate);

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

    
    @GetMapping("/search-sale-by-id/{id}")
    public ResponseEntity<List<SaleDTO>> findAllSaleByUser(@PathVariable Long id) {
        try {
        List<SaleDTO> saleList = userService.findAllSaleByUser(id);
        return ResponseEntity.ok(saleList);
        } catch  (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }


}
