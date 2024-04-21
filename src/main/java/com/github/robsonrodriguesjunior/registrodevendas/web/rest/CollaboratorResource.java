package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.dto.CollaboratorRecord;
import com.github.robsonrodriguesjunior.registrodevendas.repository.CollaboratorRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.CollaboratorQueryService;
import com.github.robsonrodriguesjunior.registrodevendas.service.CollaboratorService;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.CollaboratorCriteria;
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
 * REST controller for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator}.
 */
@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorResource {

    private final Logger log = LoggerFactory.getLogger(CollaboratorResource.class);

    private static final String ENTITY_NAME = "collaborator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaboratorService collaboratorService;

    private final CollaboratorRepository collaboratorRepository;

    private final CollaboratorQueryService collaboratorQueryService;

    public CollaboratorResource(
        CollaboratorService collaboratorService,
        CollaboratorRepository collaboratorRepository,
        CollaboratorQueryService collaboratorQueryService
    ) {
        this.collaboratorService = collaboratorService;
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorQueryService = collaboratorQueryService;
    }

    @PostMapping("")
    public ResponseEntity<CollaboratorRecord> createCollaborator(@Valid @RequestBody CollaboratorRecord collaboratorRecord)
        throws URISyntaxException {
        log.debug("REST request to save Collaborator : {}", collaboratorRecord);
        if (collaboratorRecord.id() != null) {
            throw new BadRequestAlertException("A new collaborator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collaborator result = collaboratorService.save(collaboratorRecord);
        return ResponseEntity
            .created(new URI("/api/collaborators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(CollaboratorRecord.of(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> updateCollaborator(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollaboratorRecord collaboratorRecord
    ) throws URISyntaxException {
        log.debug("REST request to update Collaborator : {}, {}", id, collaboratorRecord);
        if (collaboratorRecord.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collaboratorRecord.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collaboratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Collaborator result = collaboratorService.save(collaboratorRecord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboratorRecord.id().toString()))
            .body(CollaboratorRecord.of(result));
    }

    @GetMapping("")
    public ResponseEntity<List<CollaboratorRecord>> getAllCollaborators(
        CollaboratorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Collaborators by criteria: {}", criteria);

        Page<CollaboratorRecord> page = collaboratorQueryService.findByCriteria(criteria, pageable).map(CollaboratorRecord::of);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countCollaborators(CollaboratorCriteria criteria) {
        log.debug("REST request to count Collaborators by criteria: {}", criteria);
        return ResponseEntity.ok().body(collaboratorQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorRecord> getCollaborator(@PathVariable("id") Long id) {
        log.debug("REST request to get Collaborator : {}", id);
        Optional<Collaborator> collaborator = collaboratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaborator.map(CollaboratorRecord::of));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable("id") Long id) {
        log.debug("REST request to delete Collaborator : {}", id);
        collaboratorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
