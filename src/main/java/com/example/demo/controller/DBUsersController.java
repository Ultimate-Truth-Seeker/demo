package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.DBUser;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DBUser/")
public class DBUsersController {
    private final DBUserService serviceMongoDB;

    @Autowired
    public DBUsersController(@Autowired DBUserService serviceMongoDB) {
        this.serviceMongoDB = serviceMongoDB;
    }

    @PostMapping
    public ResponseEntity<DBUser> guardar(@RequestBody DBUser modelo) {
        DBUser user = serviceMongoDB.guardar(modelo);
        URI createdUserUri = URI.create("/v1/users/" + user.getId());
        return ResponseEntity.created(createdUserUri).body(user);
    }

    @GetMapping("{id}")
    public DBUser obtenerPorId(@PathVariable("id") String id) {
         Optional<DBUser> user = serviceMongoDB.obtenerPorId(id);
         if (user.isPresent()) {
             return user.get();
         } else {
             throw new UserNotFoundException(id);
         }

    }

    @GetMapping
    public List<DBUser> obtenerTodos() {
        return serviceMongoDB.obtenertodos();
    }

    @PutMapping("{id}")
    public ResponseEntity<DBUser> actualizar(@PathVariable String id, @RequestBody UserDto modelo) {
        Optional<DBUser> user = serviceMongoDB.obtenerPorId(id);
        if (user.isPresent()) {
            user.get().update(modelo);
            return ResponseEntity.ok(serviceMongoDB.guardar(user.get()));
        } else {
            throw new UserNotFoundException(id);
        }

    }

    @DeleteMapping("{id}")
    public void borrar(@PathVariable String id) {
        Optional<DBUser> user = serviceMongoDB.obtenerPorId(id);
        if (user.isPresent()) {
            serviceMongoDB.eliminar(id);
        } else {
            throw new UserNotFoundException(id);
        }

    }

}
