package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.TopSellingProductsViewRepository;
import com.github.robsonrodriguesjunior.registrodevendas.web.rest.errors.BadRequestAlertException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.TopSellingProductsView}.
 */
@RestController
@RequestMapping("/api/top-selling-products-views")
@Transactional
public class TopSellingProductsViewResource {

    private final Logger log = LoggerFactory.getLogger(TopSellingProductsViewResource.class);

    private static final String ENTITY_NAME = "topSellingProductsView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopSellingProductsViewRepository topSellingProductsViewRepository;

    public TopSellingProductsViewResource(TopSellingProductsViewRepository topSellingProductsViewRepository) {
        this.topSellingProductsViewRepository = topSellingProductsViewRepository;
    }

    /**
     * {@code POST  /top-selling-products-views} : Create a new topSellingProductsView.
     *
     * @param topSellingProductsView the topSellingProductsView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topSellingProductsView, or with status {@code 400 (Bad Request)} if the topSellingProductsView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TopSellingProductsView> createTopSellingProductsView(@RequestBody TopSellingProductsView topSellingProductsView)
        throws URISyntaxException {
        log.debug("REST request to save TopSellingProductsView : {}", topSellingProductsView);
        if (topSellingProductsView.getId() != null) {
            throw new BadRequestAlertException("A new topSellingProductsView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopSellingProductsView result = topSellingProductsViewRepository.save(topSellingProductsView);
        return ResponseEntity
            .created(new URI("/api/top-selling-products-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /top-selling-products-views/:id} : Updates an existing topSellingProductsView.
     *
     * @param id the id of the topSellingProductsView to save.
     * @param topSellingProductsView the topSellingProductsView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topSellingProductsView,
     * or with status {@code 400 (Bad Request)} if the topSellingProductsView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topSellingProductsView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopSellingProductsView> updateTopSellingProductsView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TopSellingProductsView topSellingProductsView
    ) throws URISyntaxException {
        log.debug("REST request to update TopSellingProductsView : {}, {}", id, topSellingProductsView);
        if (topSellingProductsView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topSellingProductsView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topSellingProductsViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TopSellingProductsView result = topSellingProductsViewRepository.save(topSellingProductsView);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topSellingProductsView.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /top-selling-products-views/:id} : Partial updates given fields of an existing topSellingProductsView, field will ignore if it is null
     *
     * @param id the id of the topSellingProductsView to save.
     * @param topSellingProductsView the topSellingProductsView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topSellingProductsView,
     * or with status {@code 400 (Bad Request)} if the topSellingProductsView is not valid,
     * or with status {@code 404 (Not Found)} if the topSellingProductsView is not found,
     * or with status {@code 500 (Internal Server Error)} if the topSellingProductsView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TopSellingProductsView> partialUpdateTopSellingProductsView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TopSellingProductsView topSellingProductsView
    ) throws URISyntaxException {
        log.debug("REST request to partial update TopSellingProductsView partially : {}, {}", id, topSellingProductsView);
        if (topSellingProductsView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topSellingProductsView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topSellingProductsViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TopSellingProductsView> result = topSellingProductsViewRepository
            .findById(topSellingProductsView.getId())
            .map(existingTopSellingProductsView -> {
                if (topSellingProductsView.getQuantity() != null) {
                    existingTopSellingProductsView.setQuantity(topSellingProductsView.getQuantity());
                }
                if (topSellingProductsView.getPosition() != null) {
                    existingTopSellingProductsView.setPosition(topSellingProductsView.getPosition());
                }

                return existingTopSellingProductsView;
            })
            .map(topSellingProductsViewRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topSellingProductsView.getId().toString())
        );
    }

    /**
     * {@code GET  /top-selling-products-views} : get all the topSellingProductsViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topSellingProductsViews in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TopSellingProductsView>> getAllTopSellingProductsViews(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TopSellingProductsViews");
        Page<TopSellingProductsView> page = topSellingProductsViewRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /top-selling-products-views/:id} : get the "id" topSellingProductsView.
     *
     * @param id the id of the topSellingProductsView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topSellingProductsView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopSellingProductsView> getTopSellingProductsView(@PathVariable("id") Long id) {
        log.debug("REST request to get TopSellingProductsView : {}", id);
        Optional<TopSellingProductsView> topSellingProductsView = topSellingProductsViewRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(topSellingProductsView);
    }

    /**
     * {@code DELETE  /top-selling-products-views/:id} : delete the "id" topSellingProductsView.
     *
     * @param id the id of the topSellingProductsView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopSellingProductsView(@PathVariable("id") Long id) {
        log.debug("REST request to delete TopSellingProductsView : {}", id);
        topSellingProductsViewRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
