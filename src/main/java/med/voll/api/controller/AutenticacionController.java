package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.application.dto.DatosAutenticacionUsuario;
import med.voll.api.domain.model.Usuario;
import med.voll.api.infrastructure.security.DatosJWTToken;
import med.voll.api.infrastructure.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticationUsuario ){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticationUsuario.login(), datosAutenticationUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authenticationToken).getPrincipal();

        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado);

        return ResponseEntity.ok(new DatosJWTToken(JWTToken));

    }
}
