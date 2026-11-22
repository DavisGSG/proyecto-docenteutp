package pe.utp.docenteutp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.docenteutp.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}