package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.repository.CollaboratorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator}.
 */
@Service
@Transactional
public class CollaboratorService {

    private final Logger log = LoggerFactory.getLogger(CollaboratorService.class);

    private final CollaboratorRepository collaboratorRepository;

    public CollaboratorService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    /**
     * Save a collaborator.
     *
     * @param collaborator the entity to save.
     * @return the persisted entity.
     */
    public Collaborator save(Collaborator collaborator) {
        log.debug("Request to save Collaborator : {}", collaborator);
        return collaboratorRepository.save(collaborator);
    }

    /**
     * Update a collaborator.
     *
     * @param collaborator the entity to save.
     * @return the persisted entity.
     */
    public Collaborator update(Collaborator collaborator) {
        log.debug("Request to update Collaborator : {}", collaborator);
        return collaboratorRepository.save(collaborator);
    }

    /**
     * Partially update a collaborator.
     *
     * @param collaborator the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Collaborator> partialUpdate(Collaborator collaborator) {
        log.debug("Request to partially update Collaborator : {}", collaborator);

        return collaboratorRepository
            .findById(collaborator.getId())
            .map(existingCollaborator -> {
                if (collaborator.getCode() != null) {
                    existingCollaborator.setCode(collaborator.getCode());
                }
                if (collaborator.getType() != null) {
                    existingCollaborator.setType(collaborator.getType());
                }
                if (collaborator.getStatus() != null) {
                    existingCollaborator.setStatus(collaborator.getStatus());
                }

                return existingCollaborator;
            })
            .map(collaboratorRepository::save);
    }

    /**
     * Get all the collaborators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Collaborator> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborators");
        return collaboratorRepository.findAll(pageable);
    }

    /**
     *  Get all the collaborators where SellersWhoEarnedMostView is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Collaborator> findAllWhereSellersWhoEarnedMostViewIsNull() {
        log.debug("Request to get all collaborators where SellersWhoEarnedMostView is null");
        return StreamSupport
            .stream(collaboratorRepository.findAll().spliterator(), false)
            .filter(collaborator -> collaborator.getSellersWhoEarnedMostView() == null)
            .toList();
    }

    /**
     *  Get all the collaborators where SellersWhoSoldMostProductsView is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Collaborator> findAllWhereSellersWhoSoldMostProductsViewIsNull() {
        log.debug("Request to get all collaborators where SellersWhoSoldMostProductsView is null");
        return StreamSupport
            .stream(collaboratorRepository.findAll().spliterator(), false)
            .filter(collaborator -> collaborator.getSellersWhoSoldMostProductsView() == null)
            .toList();
    }

    /**
     * Get one collaborator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Collaborator> findOne(Long id) {
        log.debug("Request to get Collaborator : {}", id);
        return collaboratorRepository.findById(id);
    }

    /**
     * Delete the collaborator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collaborator : {}", id);
        collaboratorRepository.deleteById(id);
    }
}
