package med.voll.web_application.infra.exception.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {


    // Este método cria os nossos próprios usuários e retorna seus dados para o objeto UserDetailsService
    @Bean
    public UserDetailsService dadosUsuariosCadastrados() {
        // Criamos dois usuários pré-definidos
        UserDetails usuario1 = User.builder()
                .username("joao@email.com")
                .password("{noop}joao123")
                .build();

        UserDetails usuario2 = User.builder()
                .username("maria@email.com")
                .password("{noop}maria123")
                .build();

        //Por enquanto, estes usuários serão salvos em memória ao invés de um BD.
        return new InMemoryUserDetailsManager(usuario1, usuario2);
    }
}
