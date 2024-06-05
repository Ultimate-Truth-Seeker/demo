package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.DBUser;
import com.example.demo.model.UserDto;
import com.example.demo.service.DBUserServiceMongoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DBUser/")
public class DBUsersController {
    private final DBUserServiceMongoDB serviceMongoDB;

    @Autowired
    public DBUsersController(DBUserServiceMongoDB serviceMongoDB) {
        this.serviceMongoDB = serviceMongoDB;
    }

    @PostMapping
    public DBUser guardar(@RequestBody DBUser modelo) {
        return serviceMongoDB.guardar(modelo);
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
    public DBUser actualizar(@PathVariable String id, @RequestBody UserDto modelo) {
        return serviceMongoDB.actualizar(id, modelo);
    }

    @DeleteMapping("{id}")
    public void borrar(@PathVariable String id) {
        serviceMongoDB.eliminar(id);
    }

}
