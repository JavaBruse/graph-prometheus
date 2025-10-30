package ru.javabruse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String mainPage(){
        return "Главная страница Спринг графана прометеус";
    }
}
