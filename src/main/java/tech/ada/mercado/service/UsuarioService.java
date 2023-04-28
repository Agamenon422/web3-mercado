package tech.ada.mercado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.mercado.model.Usuario;
import tech.ada.mercado.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Flux<Usuario> findAll() {
        return repository.findAll();
    }

    public Mono<Usuario> save(Usuario usuario) {
        return repository.save(usuario);
    }

    public Mono<Usuario> findById(String id) {
        return repository.findById(id);
    }

    public Mono<?> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("user not found id " + id)))
                .flatMap(u -> repository.deleteById(id))
                .then();
    }

}