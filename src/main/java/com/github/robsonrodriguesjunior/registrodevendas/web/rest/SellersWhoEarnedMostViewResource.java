package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoEarnedMostViewRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.SellersWhoEarnedMostViewQueryService;
import com.github.robsonrodriguesjunior.registrodevendas.service.SellersWhoEarnedMostViewService;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.SellersWhoEarnedMostViewCriteria;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoEarnedMostView}.
 */
@RestController
@RequestMapping("/api/sellers-who-earned-most-views")
public class SellersWhoEarnedMostViewResource {

    private final Logger log = LoggerFactory.getLogger(SellersWhoEarnedMostViewResource.class);

    private static final String ENTITY_NAME = "sellersWhoEarnedMostView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SellersWhoEarnedMostViewService sellersWhoEarnedMostViewService;

    private final SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository;

    private final SellersWhoEarnedMostViewQueryService sellersWhoEarnedMostViewQueryService;

    public SellersWhoEarnedMostViewResource(
        SellersWhoEarnedMostViewService sellersWhoEarnedMostViewService,
        SellersWhoEarnedMostViewRepository sellersWhoEarnedMostViewRepository,
        SellersWhoEarnedMostViewQueryService sellersWhoEarnedMostViewQueryService
    ) {
        this.sellersWhoEarnedMostViewService = sellersWhoEarnedMostViewService;
        this.sellersWhoEarnedMostViewRepository = sellersWhoEarnedMostViewRepository;
        this.sellersWhoEarnedMostViewQueryService = sellersWhoEarnedMostViewQueryService;
    }

    /**
     * {@code POST  /sellers-who-earned-most-views} : Create a new sellersWhoEarnedMostView.
     *
     * @param sellersWhoEarnedMostView the sellersWhoEarnedMostView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellersWhoEarnedMostView, or with status {@code 400 (Bad Request)} if the sellersWhoEarnedMostView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SellersWhoEarnedMostView> createSellersWhoEarnedMostView(
        @RequestBody SellersWhoEarnedMostView sellersWhoEarnedMostView
    ) throws URISyntaxException {
        log.debug("REST request to save SellersWhoEarnedMostView : {}", sellersWhoEarnedMostView);
        if (sellersWhoEarnedMostView.getId() != null) {
            throw new BadRequestAlertException("A new sellersWhoEarnedMostView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellersWhoEarnedMostView result = sellersWhoEarnedMostViewService.save(sellersWhoEarnedMostView);
        return ResponseEntity
            .created(new URI("/api/sellers-who-earned-most-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sellers-who-earned-most-views/:id} : Updates an existing sellersWhoEarnedMostView.
     *
     * @param id the id of the sellersWhoEarnedMostView to save.
     * @param sellersWhoEarnedMostView the sellersWhoEarnedMostView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellersWhoEarnedMostView,
     * or with status {@code 400 (Bad Request)} if the sellersWhoEarnedMostView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sellersWhoEarnedMostView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SellersWhoEarnedMostView> updateSellersWhoEarnedMostView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SellersWhoEarnedMostView sellersWhoEarnedMostView
    ) throws URISyntaxException {
        log.debug("REST request to update SellersWhoEarnedMostView : {}, {}", id, sellersWhoEarnedMostView);
        if (sellersWhoEarnedMostView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellersWhoEarnedMostView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellersWhoEarnedMostViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SellersWhoEarnedMostView result = sellersWhoEarnedMostViewService.update(sellersWhoEarnedMostView);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sellersWhoEarnedMostView.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sellers-who-earned-most-views/:id} : Partial updates given fields of an existing sellersWhoEarnedMostView, field will ignore if it is null
     *
     * @param id the id of the sellersWhoEarnedMostView to save.
     * @param sellersWhoEarnedMostView the sellersWhoEarnedMostView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellersWhoEarnedMostView,
     * or with status {@code 400 (Bad Request)} if the sellersWhoEarnedMostView is not valid,
     * or with status {@code 404 (Not Found)} if the sellersWhoEarnedMostView is not found,
     * or with status {@code 500 (Internal Server Error)} if the sellersWhoEarnedMostView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SellersWhoEarnedMostView> partialUpdateSellersWhoEarnedMostView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SellersWhoEarnedMostView sellersWhoEarnedMostView
    ) throws URISyntaxException {
        log.debug("REST request to partial update SellersWhoEarnedMostView partially : {}, {}", id, sellersWhoEarnedMostView);
        if (sellersWhoEarnedMostView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellersWhoEarnedMostView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellersWhoEarnedMostViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SellersWhoEarnedMostView> result = sellersWhoEarnedMostViewService.partialUpdate(sellersWhoEarnedMostView);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sellersWhoEarnedMostView.getId().toString())
        );
    }

    /**
     * {@code GET  /sellers-who-earned-most-views} : get all the sellersWhoEarnedMostViews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sellersWhoEarnedMostViews in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SellersWhoEarnedMostView>> getAllSellersWhoEarnedMostViews(
        SellersWhoEarnedMostViewCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SellersWhoEarnedMostViews by criteria: {}", criteria);

        Page<SellersWhoEarnedMostView> page = sellersWhoEarnedMostViewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sellers-who-earned-most-views/count} : count all the sellersWhoEarnedMostViews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSellersWhoEarnedMostViews(SellersWhoEarnedMostViewCriteria criteria) {
        log.debug("REST request to count SellersWhoEarnedMostViews by criteria: {}", criteria);
        return ResponseEntity.ok().body(sellersWhoEarnedMostViewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sellers-who-earned-most-views/:id} : get the "id" sellersWhoEarnedMostView.
     *
     * @param id the id of the sellersWhoEarnedMostView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sellersWhoEarnedMostView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SellersWhoEarnedMostView> getSellersWhoEarnedMostView(@PathVariable("id") Long id) {
        log.debug("REST request to get SellersWhoEarnedMostView : {}", id);
        Optional<SellersWhoEarnedMostView> sellersWhoEarnedMostView = sellersWhoEarnedMostViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sellersWhoEarnedMostView);
    }

    /**
     * {@code DELETE  /sellers-who-earned-most-views/:id} : delete the "id" sellersWhoEarnedMostView.
     *
     * @param id the id of the sellersWhoEarnedMostView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellersWhoEarnedMostView(@PathVariable("id") Long id) {
        log.debug("REST request to delete SellersWhoEarnedMostView : {}", id);
        sellersWhoEarnedMostViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
