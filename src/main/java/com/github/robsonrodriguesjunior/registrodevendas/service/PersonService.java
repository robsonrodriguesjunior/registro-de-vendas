package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.repository.PersonRepository;
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
 * Service Implementation for managing {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Person}.
 */
@Service
@Transactional
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Save a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Update a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    public Person update(Person person) {
        log.debug("Request to update Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Partially update a person.
     *
     * @param person the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Person> partialUpdate(Person person) {
        log.debug("Request to partially update Person : {}", person);

        return personRepository
            .findById(person.getId())
            .map(existingPerson -> {
                if (person.getFirstName() != null) {
                    existingPerson.setFirstName(person.getFirstName());
                }
                if (person.getSecondName() != null) {
                    existingPerson.setSecondName(person.getSecondName());
                }
                if (person.getBirthday() != null) {
                    existingPerson.setBirthday(person.getBirthday());
                }

                return existingPerson;
            })
            .map(personRepository::save);
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Person> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return personRepository.findAll(pageable);
    }

    /**
     *  Get all the people where Client is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Person> findAllWhereClientIsNull() {
        log.debug("Request to get all people where Client is null");
        return StreamSupport.stream(personRepository.findAll().spliterator(), false).filter(person -> person.getClient() == null).toList();
    }

    /**
     *  Get all the people where Seller is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Person> findAllWhereSellerIsNull() {
        log.debug("Request to get all people where Seller is null");
        return StreamSupport.stream(personRepository.findAll().spliterator(), false).filter(person -> person.getSeller() == null).toList();
    }

    /**
     * Get one person by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Person> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findById(id);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }
}
