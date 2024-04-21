package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Client;
import com.github.robsonrodriguesjunior.registrodevendas.dto.ClientRecord;
import com.github.robsonrodriguesjunior.registrodevendas.repository.ClientRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.ClientQueryService;
import com.github.robsonrodriguesjunior.registrodevendas.service.ClientService;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.ClientCriteria;
import com.github.robsonrodriguesjunior.registrodevendas.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Client}.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientService clientService;

    private final ClientRepository clientRepository;

    private final ClientQueryService clientQueryService;

    public ClientResource(ClientService clientService, ClientRepository clientRepository, ClientQueryService clientQueryService) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.clientQueryService = clientQueryService;
    }

    @PostMapping("")
    public ResponseEntity<ClientRecord> createClient(@Valid @RequestBody ClientRecord clientRecord) throws URISyntaxException {
        log.debug("REST request to save Client : {}", clientRecord);
        if (clientRecord.id() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Client result = clientService.save(clientRecord);
        return ResponseEntity
            .created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(ClientRecord.of(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientRecord> updateClient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClientRecord clientRecord
    ) throws URISyntaxException {
        log.debug("REST request to update Client : {}, {}", id, clientRecord);
        if (clientRecord.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientRecord.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Client result = clientService.save(clientRecord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(ClientRecord.of(result));
    }

    @GetMapping("")
    public ResponseEntity<List<ClientRecord>> getAllClients(
        ClientCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Clients by criteria: {}", criteria);

        Page<ClientRecord> page = clientQueryService.findByCriteria(criteria, pageable).map(ClientRecord::of);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countClients(ClientCriteria criteria) {
        log.debug("REST request to count Clients by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRecord> getClient(@PathVariable("id") Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client.map(ClientRecord::of));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
