package com.github.robsonrodriguesjunior.registrodevendas.service;

import com.github.robsonrodriguesjunior.registrodevendas.domain.Client;
import com.github.robsonrodriguesjunior.registrodevendas.domain.Person;
import com.github.robsonrodriguesjunior.registrodevendas.dto.ClientRecord;
import com.github.robsonrodriguesjunior.registrodevendas.repository.ClientRepository;
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
 * {@link com.github.robsonrodriguesjunior.registrodevendas.domain.Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final PersonRepository personRepository;

    public ClientService(final ClientRepository clientRepository, final PersonRepository personRepository) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
    }

    public Client save(@Nonnull final ClientRecord clientRecord) {
        log.debug("Request to save Client : {}", clientRecord);

        final var client = new Client(clientRecord);

        personRepository.findOneByCpf(clientRecord.cpf()).ifPresentOrElse(client::setPerson, () -> client.setPerson(new Person()));

        client.getPerson().setBirthday(clientRecord.birthday());
        client.getPerson().setCpf(clientRecord.cpf());
        client.getPerson().setFirstName(clientRecord.firstName());
        client.getPerson().setSecondName(clientRecord.secondName());
        client.setCode(clientRecord.code());

        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
