package med.voll.web_application.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método que procura o usuário por e-mail: utiliza Devirate Carries do JPA
    // Procuramos pelo e-mail e ignora se o usuário digitou maiúsculo ou minúsculo

    Optional<Usuario> findByEmailIgnoreCase(String email);
}