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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {
    @Bean
    // Este método HTTP já possui todos os filtros padrão do Spring
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        // Autorizamos realizar requisições HTTP na aplicação
        return http.authorizeHttpRequests(req -> {
            req.requestMatchers("/css/**", "/js/**", "/assets/**").permitAll(); // Informamos ao Spring para não perder a configuração do front-end
            req.requestMatchers("/", "/index", "/home").permitAll();
            req.anyRequest().authenticated(); // As requisições são bloqueadas por padrão, enquanto o usuário não estiver logado
            })

                // Passaremos um formulário customizado parametrizado
                .formLogin(form -> form.loginPage("/login")
                .defaultSuccessUrl("/") // Após o login, manda para home
                .permitAll()) // Permite o acesso à aplicação após o sucesso no login
                .logout(logout ->
                        logout.logoutSuccessUrl("/login?logout") // Criamos um objeto que redireciona para o endereço
                        .permitAll()) // Permitimos que o usuário faça o login novamente pela mesma página
                .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim") // Construímos um objeto com a chave que utilizamos para gerar o cookie RememberMe
                                                // É recomendado utilizar um secret que será utilizado para codificar a chave aleatória, tendo ela, é possível decodificar e entender quem é este cookie
                        //.tokenValiditySeconds(30) // Tempo em segundos para a expiração do cookie RememberMe
                        .alwaysRemember(true))
                .build();
    }

    @Bean
    public PasswordEncoder codificadorDeSenha() {
        // O codificador precisa retornar uma instância de um password encoder.
        return new BCryptPasswordEncoder();
    }
}