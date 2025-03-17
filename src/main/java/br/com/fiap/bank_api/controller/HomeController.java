package br.com.fiap.bank_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "Projeto: Bank API - Integrantes: Afonso Correia Pereira e Tiago Ferro";
    }
}
