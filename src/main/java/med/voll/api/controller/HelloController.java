package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String helloWorld(){
        return "¡Bienvenido a nuestra API! 🚀\n" +
               "Estamos emocionados de presentarte esta aplicación desarrollada con Java y Spring Boot. " +
               "¡Esperamos que tengas una excelente experiencia! 💻✨";
    }
}
