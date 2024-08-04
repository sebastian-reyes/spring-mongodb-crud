package com.sreyes.app.api_rest_reactiva_mongodb.controller;

import com.sreyes.app.api_rest_reactiva_mongodb.model.Contacto;
import com.sreyes.app.api_rest_reactiva_mongodb.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping("/contactos")
    public Flux<Contacto> getContactos() {
        return contactoRepository.findAll();
    }

    @GetMapping("/contactos/{id}")
    public Mono<ResponseEntity<Contacto>> getContacto(@PathVariable String id) {
        return contactoRepository.findById(id)
                .map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/contactos/byEmail/{email}")
    public Mono<ResponseEntity<Contacto>> getContactoByEmail(@PathVariable String email) {
        return contactoRepository.findFirstByEmail(email)
                .map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contactos")
    public Mono<ResponseEntity<Contacto>> createContacto(@RequestBody Contacto contacto) {
        return contactoRepository.insert(contacto)
                .map(contactoGuardado -> new ResponseEntity<>(contactoGuardado, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    }

    @PutMapping("/contactos/{id}")
    public Mono<ResponseEntity<Contacto>> updateContacto(@PathVariable String id, @RequestBody Contacto contacto) {
        return contactoRepository.findById(id)
                .flatMap(contactoActualizado ->{
                    contacto.setId(id);
                    return contactoRepository.save(contacto)
                            .map(contactoGuardado -> new ResponseEntity<>(contactoGuardado, HttpStatus.OK));
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/contactos/{id}")
    public Mono<Void> deleteContacto(@PathVariable String id) {
        return contactoRepository.deleteById(id);
    }
}
