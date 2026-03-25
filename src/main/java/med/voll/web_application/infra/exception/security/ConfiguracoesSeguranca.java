package med.voll.web_application.infra.exception.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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

    @Bean
    // Este método HTTP já possui todos os filtros padrão do Spring
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        // Autorizamos realizar requisições HTTP na aplicação
        return http.authorizeHttpRequests(req -> {
            req.requestMatchers("/css/**", "/js/**", "/assets/**").permitAll(); // Informamos ao Spring para não perder a configuração do front-end
            req.anyRequest().authenticated(); // As requisições são bloqueadas por padrão, enquanto o usuário não estiver logado
            })

                // Passaremos um formulário customizado parametrizado
                .formLogin(form -> form.loginPage("/login")
                .defaultSuccessUrl("/") // Após o login, manda para home
                .permitAll()) // Permite o acesso à aplicação após o sucesso no login
                .logout(logout ->
                        logout.logoutSuccessUrl("/login?logout") // Crianos um objeto que redireciona para o endereço
                        .permitAll()) // Permitimos que o usuário faça o login novamente pela mesma página
                .build();
    }
}