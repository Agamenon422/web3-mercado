package tech.ada.mercado.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tech.ada.mercado.model.Usuario;

public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {
}