package com.desafiocriente.DSCliente.repositores;

import com.desafiocriente.DSCliente.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
