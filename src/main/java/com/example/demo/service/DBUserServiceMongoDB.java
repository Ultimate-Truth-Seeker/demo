package com.example.demo.service;

import com.example.demo.model.DBUser;
import com.example.demo.model.UserDto;
import com.example.demo.repository.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DBUserServiceMongoDB {
    private final users repository;

    @Autowired
    public DBUserServiceMongoDB(users repository) {
        this.repository = repository;
    }

    public DBUser guardar(DBUser modelo) {
        return repository.save(modelo);
    }

    public Optional<DBUser> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public List<DBUser> obtenertodos() {
        return repository.findAll();
    }

    public DBUser actualizar(String id, UserDto user) {
        DBUser u = repository.findById(id).orElse(null);
        assert u != null;
        u.update(user);
        return repository.save(u);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }


}
