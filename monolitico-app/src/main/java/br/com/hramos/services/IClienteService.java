package br.com.hramos.services;

import br.com.hramos.domain.Cliente;
import br.com.hramos.exceptions.DAOException;
import br.com.hramos.services.generic.IGenericService;

import java.util.List;

public interface IClienteService extends IGenericService<Cliente, Long> {

	Cliente buscarPorCPF(Long cpf) throws DAOException;

	List<Cliente> filtrarClientes(String query);

}
