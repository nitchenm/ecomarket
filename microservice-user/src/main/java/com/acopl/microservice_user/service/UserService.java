package com.acopl.microservice_user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acopl.microservice_user.client.clientSale;
import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private clientSale clientSale;

    //obtiene todos los usuarios
    public List<User> findall() {
        return userRepository.findAll();
    }

    //obtiene un user por su id
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }

    //guarda un usuario
    public User save(User user){
        //¿Esto se podrá mejorar? ¿Manejo de errores?
        return userRepository.save(user);
    }

    //recuerda, es un void porque no necesitamos que retorne algo cuando
    //se elimina
    // Se elimina un usuario por su id
    public void deleteById(Long id){
        //¿Esto se podrá mejorar? ¿Manejo de errores?
        userRepository.deleteById(id);
    }

    //se actualiza usuario
    public User updateUser(Long id, User updatedUser){
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRol(updatedUser.getRol());
        existingUser.setPermisos(updatedUser.getPermisos());

        return userRepository.save(existingUser);
    }


    public boolean authenticateById(Long id, String email, String rol) {
    return userRepository.findById(id)
            .map(user ->
                user.getEmail().equals(email) && user.getRol().equals(rol)
            ).orElse(false);
    }

    public List<SaleDTO> findAllSaleByUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

        List<SaleDTO> saleDTOList = clientSale.findAllSaleByUser(id);

        return saleDTOList;
    }
}
