package com.github.robsonrodriguesjunior.registrodevendas.web.rest;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Sale;
import com.github.robsonrodriguesjunior.registrodevendas.repository.SaleRepository;
import com.github.robsonrodriguesjunior.registrodevendas.service.SaleQueryService;
import com.github.robsonrodriguesjunior.registrodevendas.service.SaleService;
import com.github.robsonrodriguesjunior.registrodevendas.service.criteria.SaleCriteria;
import com.github.robsonrodriguesjunior.registrodevendas.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Sale}.
 */
@RestController
@RequestMapping("/api/sales")
public class SaleResource {

    private final Logger log = LoggerFactory.getLogger(SaleResource.class);

    private static final String ENTITY_NAME = "sale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleService saleService;

    private final SaleRepository saleRepository;

    private final SaleQueryService saleQueryService;

    public SaleResource(SaleService saleService, SaleRepository saleRepository, SaleQueryService saleQueryService) {
        this.saleService = saleService;
        this.saleRepository = saleRepository;
        this.saleQueryService = saleQueryService;
    }

    /**
     * {@code POST  /sales} : Create a new sale.
     *
     * @param sale the sale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sale, or with status {@code 400 (Bad Request)} if the sale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sale> createSale(@Valid @RequestBody Sale sale) throws URISyntaxException {
        log.debug("REST request to save Sale : {}", sale);
        if (sale.getId() != null) {
            throw new BadRequestAlertException("A new sale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sale result = saleService.save(sale);
        return ResponseEntity
            .created(new URI("/api/sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales/:id} : Updates an existing sale.
     *
     * @param id the id of the sale to save.
     * @param sale the sale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sale,
     * or with status {@code 400 (Bad Request)} if the sale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Sale sale)
        throws URISyntaxException {
        log.debug("REST request to update Sale : {}, {}", id, sale);
        if (sale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sale result = saleService.update(sale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales/:id} : Partial updates given fields of an existing sale, field will ignore if it is null
     *
     * @param id the id of the sale to save.
     * @param sale the sale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sale,
     * or with status {@code 400 (Bad Request)} if the sale is not valid,
     * or with status {@code 404 (Not Found)} if the sale is not found,
     * or with status {@code 500 (Internal Server Error)} if the sale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sale> partialUpdateSale(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sale sale
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sale partially : {}, {}", id, sale);
        if (sale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sale> result = saleService.partialUpdate(sale);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sale.getId().toString())
        );
    }

    /**
     * {@code GET  /sales} : get all the sales.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sales in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Sale>> getAllSales(
        SaleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Sales by criteria: {}", criteria);

        Page<Sale> page = saleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales/count} : count all the sales.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSales(SaleCriteria criteria) {
        log.debug("REST request to count Sales by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales/:id} : get the "id" sale.
     *
     * @param id the id of the sale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable("id") Long id) {
        log.debug("REST request to get Sale : {}", id);
        Optional<Sale> sale = saleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sale);
    }

    /**
     * {@code DELETE  /sales/:id} : delete the "id" sale.
     *
     * @param id the id of the sale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sale : {}", id);
        saleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
