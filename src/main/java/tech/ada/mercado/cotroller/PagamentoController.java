package tech.ada.mercado.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.ada.mercado.model.Comprovante;
import tech.ada.mercado.model.Pagamento;
import tech.ada.mercado.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
@Slf4j
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public Mono<Comprovante> pagar(@RequestBody Pagamento pagamento) {
        return service.pagar(pagamento);
    }

}