package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SellersWhoSoldMostProductsViewRepository;
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
 * REST controller for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.SellersWhoSoldMostProductsView}.
 */
@RestController
@RequestMapping("/api/sellers-who-sold-most-products-views")
@Transactional
public class SellersWhoSoldMostProductsViewResource {

    private final Logger log = LoggerFactory.getLogger(SellersWhoSoldMostProductsViewResource.class);

    private static final String ENTITY_NAME = "sellersWhoSoldMostProductsView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository;

    public SellersWhoSoldMostProductsViewResource(SellersWhoSoldMostProductsViewRepository sellersWhoSoldMostProductsViewRepository) {
        this.sellersWhoSoldMostProductsViewRepository = sellersWhoSoldMostProductsViewRepository;
    }

    /**
     * {@code POST  /sellers-who-sold-most-products-views} : Create a new sellersWhoSoldMostProductsView.
     *
     * @param sellersWhoSoldMostProductsView the sellersWhoSoldMostProductsView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellersWhoSoldMostProductsView, or with status {@code 400 (Bad Request)} if the sellersWhoSoldMostProductsView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SellersWhoSoldMostProductsView> createSellersWhoSoldMostProductsView(
        @RequestBody SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView
    ) throws URISyntaxException {
        log.debug("REST request to save SellersWhoSoldMostProductsView : {}", sellersWhoSoldMostProductsView);
        if (sellersWhoSoldMostProductsView.getId() != null) {
            throw new BadRequestAlertException("A new sellersWhoSoldMostProductsView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellersWhoSoldMostProductsView result = sellersWhoSoldMostProductsViewRepository.save(sellersWhoSoldMostProductsView);
        return ResponseEntity
            .created(new URI("/api/sellers-who-sold-most-products-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sellers-who-sold-most-products-views/:id} : Updates an existing sellersWhoSoldMostProductsView.
     *
     * @param id the id of the sellersWhoSoldMostProductsView to save.
     * @param sellersWhoSoldMostProductsView the sellersWhoSoldMostProductsView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellersWhoSoldMostProductsView,
     * or with status {@code 400 (Bad Request)} if the sellersWhoSoldMostProductsView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sellersWhoSoldMostProductsView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SellersWhoSoldMostProductsView> updateSellersWhoSoldMostProductsView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView
    ) throws URISyntaxException {
        log.debug("REST request to update SellersWhoSoldMostProductsView : {}, {}", id, sellersWhoSoldMostProductsView);
        if (sellersWhoSoldMostProductsView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellersWhoSoldMostProductsView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellersWhoSoldMostProductsViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SellersWhoSoldMostProductsView result = sellersWhoSoldMostProductsViewRepository.save(sellersWhoSoldMostProductsView);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sellersWhoSoldMostProductsView.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /sellers-who-sold-most-products-views/:id} : Partial updates given fields of an existing sellersWhoSoldMostProductsView, field will ignore if it is null
     *
     * @param id the id of the sellersWhoSoldMostProductsView to save.
     * @param sellersWhoSoldMostProductsView the sellersWhoSoldMostProductsView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellersWhoSoldMostProductsView,
     * or with status {@code 400 (Bad Request)} if the sellersWhoSoldMostProductsView is not valid,
     * or with status {@code 404 (Not Found)} if the sellersWhoSoldMostProductsView is not found,
     * or with status {@code 500 (Internal Server Error)} if the sellersWhoSoldMostProductsView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SellersWhoSoldMostProductsView> partialUpdateSellersWhoSoldMostProductsView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SellersWhoSoldMostProductsView sellersWhoSoldMostProductsView
    ) throws URISyntaxException {
        log.debug("REST request to partial update SellersWhoSoldMostProductsView partially : {}, {}", id, sellersWhoSoldMostProductsView);
        if (sellersWhoSoldMostProductsView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellersWhoSoldMostProductsView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellersWhoSoldMostProductsViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SellersWhoSoldMostProductsView> result = sellersWhoSoldMostProductsViewRepository
            .findById(sellersWhoSoldMostProductsView.getId())
            .map(existingSellersWhoSoldMostProductsView -> {
                if (sellersWhoSoldMostProductsView.getQuantity() != null) {
                    existingSellersWhoSoldMostProductsView.setQuantity(sellersWhoSoldMostProductsView.getQuantity());
                }
                if (sellersWhoSoldMostProductsView.getPosition() != null) {
                    existingSellersWhoSoldMostProductsView.setPosition(sellersWhoSoldMostProductsView.getPosition());
                }

                return existingSellersWhoSoldMostProductsView;
            })
            .map(sellersWhoSoldMostProductsViewRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sellersWhoSoldMostProductsView.getId().toString())
        );
    }

    /**
     * {@code GET  /sellers-who-sold-most-products-views} : get all the sellersWhoSoldMostProductsViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sellersWhoSoldMostProductsViews in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SellersWhoSoldMostProductsView>> getAllSellersWhoSoldMostProductsViews(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SellersWhoSoldMostProductsViews");
        Page<SellersWhoSoldMostProductsView> page = sellersWhoSoldMostProductsViewRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sellers-who-sold-most-products-views/:id} : get the "id" sellersWhoSoldMostProductsView.
     *
     * @param id the id of the sellersWhoSoldMostProductsView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sellersWhoSoldMostProductsView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SellersWhoSoldMostProductsView> getSellersWhoSoldMostProductsView(@PathVariable("id") Long id) {
        log.debug("REST request to get SellersWhoSoldMostProductsView : {}", id);
        Optional<SellersWhoSoldMostProductsView> sellersWhoSoldMostProductsView = sellersWhoSoldMostProductsViewRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sellersWhoSoldMostProductsView);
    }

    /**
     * {@code DELETE  /sellers-who-sold-most-products-views/:id} : delete the "id" sellersWhoSoldMostProductsView.
     *
     * @param id the id of the sellersWhoSoldMostProductsView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellersWhoSoldMostProductsView(@PathVariable("id") Long id) {
        log.debug("REST request to delete SellersWhoSoldMostProductsView : {}", id);
        sellersWhoSoldMostProductsViewRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
