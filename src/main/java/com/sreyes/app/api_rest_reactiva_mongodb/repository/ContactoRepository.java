package com.sreyes.app.api_rest_reactiva_mongodb.repository;

import com.sreyes.app.api_rest_reactiva_mongodb.model.Contacto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ContactoRepository extends ReactiveMongoRepository<Contacto, String> {
    Mono<Contacto> findFirstByEmail(String email);
    Mono<Contacto> findAllByTelefonoOrNombre(String telefonoOrNombre);
}
