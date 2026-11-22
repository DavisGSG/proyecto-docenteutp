package pe.utp.docenteutp.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.utp.docenteutp.dto.AuthResponse;
import pe.utp.docenteutp.dto.LoginRequest;
import pe.utp.docenteutp.dto.RegistroRequest;
import pe.utp.docenteutp.exception.ApiException;
import pe.utp.docenteutp.model.Rol;
import pe.utp.docenteutp.model.Usuario;
import pe.utp.docenteutp.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public AuthResponse registrar(RegistroRequest req) {
        String correo = req.correo().trim().toLowerCase();

        if (!correo.endsWith("@utp.edu.pe")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Debes usar tu correo institucional @utp.edu.pe");
        }
        if (usuarioRepo.existsByCorreo(correo)) {
            throw new ApiException(HttpStatus.CONFLICT, "Ese correo ya está registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(req.nombre().trim());
        u.setCorreo(correo);
        u.setPassword(encoder.encode(req.password()));
        u.setCarrera(req.carrera().trim());
        u.setCiclo(req.ciclo());
        u.setRol(Rol.ESTUDIANTE);

        return aResponse(usuarioRepo.save(u));
    }

    public AuthResponse login(LoginRequest req) {
        Usuario u = usuarioRepo.findByCorreo(req.correo().trim().toLowerCase())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));

        if (!encoder.matches(req.password(), u.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
        }
        return aResponse(u);
    }

    private AuthResponse aResponse(Usuario u) {
        return new AuthResponse(u.getId(), u.getNombre(), u.getCorreo(),
                u.getCarrera(), u.getCiclo(), u.getRol().name());
    }
}