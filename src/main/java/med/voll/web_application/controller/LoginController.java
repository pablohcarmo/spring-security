package med.voll.web_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping ("/login")
    public String carregaPaginaListagem(){
        //Método para mapear e carregar a página de login já criada
        return "autenticacao/login";
    }
}