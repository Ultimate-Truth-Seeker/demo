package com.example.demo.service;

import com.example.demo.model.DBUser;
import com.example.demo.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface DBUserService {
    DBUser guardar(DBUser modelo);
    Optional<DBUser> obtenerPorId(String id);
    List<DBUser> obtenertodos();
    DBUser actualizar(String id, UserDto user);
    void eliminar(String id);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
