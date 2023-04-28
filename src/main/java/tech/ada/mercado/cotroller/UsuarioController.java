package tech.ada.mercado.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ada.mercado.exceptions.NotFoundException;
import tech.ada.mercado.model.Usuario;
import tech.ada.mercado.service.UsuarioService;

@RestController
@Slf4j
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Usuario>> save(@RequestBody Usuario usuario) {
        return service.save(usuario).map(u -> ResponseEntity.ok().body(u));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<ResponseEntity<Flux<Usuario>>> findAll(){
        return service.findAll()
                .collectList()
                .map(u -> ResponseEntity.ok().body(Flux.fromIterable(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Usuario>> findById(@PathVariable String id){
        return service.findById(id)
                .map(u -> ResponseEntity.ok().body(u))
                .switchIfEmpty(Mono.error(NotFoundException::new));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return service.delete(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .onErrorResume(e -> Mono.error(InternalError::new));
    }

}