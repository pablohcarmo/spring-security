package med.voll.web_application.domain.usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Injeção de dependência
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Este método precisa entender como buscaremos o usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                //Esta exceção procura pelo usuário e lança uma exceção específica caso ele não seja encontrado

                // Utilizar o Optional (no UsuarioRepository), permite utilizar o .orElseThrow, utilizar apenas Usuario
                    // náo permite utilizar exceções personalizdas.
                .orElseThrow( () -> new UsernameNotFoundException("O usuário não foi encontrado"));
    }
}