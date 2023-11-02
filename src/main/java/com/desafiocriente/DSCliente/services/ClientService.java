package com.desafiocriente.DSCliente.services;

import com.desafiocriente.DSCliente.dto.ClientDTO;
import com.desafiocriente.DSCliente.entities.Client;
import com.desafiocriente.DSCliente.repositores.ClientRepository;
import com.desafiocriente.DSCliente.services.exception.DataBaseException;
import com.desafiocriente.DSCliente.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {

        Client result = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ClientDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPage(Pageable pageable) {

        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }

    public void CopyDTOToEntity(ClientDTO dto, Client entity) {

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();

        CopyDTOToEntity(dto, entity);
        entity = repository.save(entity);

        return new ClientDTO(entity);

    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            CopyDTOToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }


        @Transactional(propagation = Propagation.SUPPORTS)
        public void deleteById(Long id) {
            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException("Recurso não encontrado");
            }
            try {
                repository.deleteById(id);
            }
            catch (DataIntegrityViolationException e) {
                throw new DataBaseException("Falha de integridade referencial");
            }
            repository.deleteById(id);
        }
    }

