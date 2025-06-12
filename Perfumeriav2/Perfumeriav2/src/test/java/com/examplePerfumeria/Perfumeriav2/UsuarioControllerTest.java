package com.examplePerfumeria.Perfumeriav2;


import com.examplePerfumeria.Perfumeriav2.registroUsuario.controller.UsuarioController;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper;

}
