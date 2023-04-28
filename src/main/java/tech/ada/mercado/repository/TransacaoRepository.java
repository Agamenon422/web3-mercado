package tech.ada.mercado.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tech.ada.mercado.model.Transacao;

public interface TransacaoRepository extends ReactiveMongoRepository<Transacao, String> {

}