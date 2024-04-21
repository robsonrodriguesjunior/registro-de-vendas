package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.dto.CollaboratorRecord;
import com.github.robsonrodriguesjunior.registrodevendas.repository.CollaboratorRepository;
import com.github.robsonrodriguesjunior.registrodevendas.repository.PersonRepository;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Collaborator}.
 */
@Service
@Transactional
public class CollaboratorService {

    private final Logger log = LoggerFactory.getLogger(CollaboratorService.class);

    private final CollaboratorRepository collaboratorRepository;

    private final PersonRepository personRepository;

    public CollaboratorService(final CollaboratorRepository collaboratorRepository, final PersonRepository personRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.personRepository = personRepository;
    }

    public Collaborator save(@Nonnull final CollaboratorRecord collaboratorRecord) {
        log.debug("Request to save Collaborator : {}", collaboratorRecord);

        final var collaborator = new Collaborator();

        personRepository
            .findOneByCpf(collaboratorRecord.cpf())
            .ifPresentOrElse(collaborator::setPerson, () -> collaborator.setPerson(new Person()));

        collaborator.getPerson().setBirthday(collaboratorRecord.birthday());
        collaborator.getPerson().setCpf(collaboratorRecord.cpf());
        collaborator.getPerson().setFirstName(collaboratorRecord.firstName());
        collaborator.getPerson().setSecondName(collaboratorRecord.secondName());
        collaborator.setCode(collaboratorRecord.code());
        collaborator.setType(collaboratorRecord.type());
        collaborator.setStatus(collaboratorRecord.status());

        return collaboratorRepository.save(collaborator);
    }

    @Transactional(readOnly = true)
    public Page<Collaborator> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborators");
        return collaboratorRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Collaborator> findOne(Long id) {
        log.debug("Request to get Collaborator : {}", id);
        return collaboratorRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Collaborator : {}", id);
        collaboratorRepository.deleteById(id);
    }
}
