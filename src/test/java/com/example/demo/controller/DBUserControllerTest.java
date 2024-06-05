package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.DBUser;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import com.example.demo.service.DBUserService;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class DBUserControllerTest {
    final String BASE_URL = "/DBUser/";
    @MockBean
    private DBUserService usersService;

    @Autowired
    private DBUsersController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testFindByIdExistingUser() throws Exception {
        DBUser user = new DBUser("1", "Ada", "Lovelace", "ada@mail.com", "123456789");
        when(usersService.obtenerPorId("1")).thenReturn(Optional.of(user));

        mockMvc.perform(get(BASE_URL + "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Ada")))
                .andExpect(jsonPath("$.lastName", is("Lovelace")));

        verify(usersService, times(1)).obtenerPorId("1");
    }

    @Test
    public void testFindByIdNotExistingUser() throws Exception {
        String id = "511";
        when(usersService.obtenerPorId(id)).thenReturn(Optional.empty());


        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(1)).obtenerPorId(id);

    }


    @Test
    public void testSaveNewUser() throws Exception {
        DBUser user = new DBUser(null, "Ada", "Lovelace", "ada@mail.com", "123456789");

        when(usersService.guardar(any())).thenReturn(user);

        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(usersService, times(1)).guardar(any());
    }

    @Test
    public void testUpdateExistingUser() throws Exception {
        UserDto userDto = new UserDto( "Ada", "lovelace", "ada@mail.com", "123456789");
        DBUser user = new DBUser("1", "Ada", "Lovelace", "ada@mail.com", "123456789");

        when(usersService.obtenerPorId(user.getId())).thenReturn(Optional.of(user));

        String json = "{\"name\":\"Ada\",\"lastName\":\"lovelace\"}";
        mockMvc.perform(put(BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(usersService, times(1)).guardar(user);
    }

    @Test
    public void testUpdateNotExistingUser() throws Exception {
        String id = "1";
        when(usersService.obtenerPorId(id)).thenReturn(Optional.empty());
        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";
        mockMvc.perform(put(BASE_URL + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(0)).guardar(any());
    }

    @Test
    public void testDeleteExistingUser() throws Exception {
        DBUser user= new DBUser("1","Ada", "Lovelace", "ada@mail.com", "123456789");
        when(usersService.obtenerPorId("1")).thenReturn(Optional.of(user));

        String json = "{\"id\":\"1\",\"name\":\"Ada\",\"lastName\":\"Lovelace\"}";
        mockMvc.perform(delete(BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(usersService, times(1)).eliminar("1");
    }

    @Test
    public void testDeleteNotExistingUser() throws Exception {
        String id = "1";
        when(usersService.obtenerPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"user with ID: " + id + " not found\"", result.getResolvedException().getMessage()));

        verify(usersService, times(0)).eliminar(id);
    }


}

