package br.com.hramos.dao;

import br.com.hramos.dao.generic.IGenericDAO;
import br.com.hramos.domain.Cliente;

import java.util.List;

public interface IClienteDAO extends IGenericDAO<Cliente, Long> {

	List<Cliente> filtrarClientes(String query);

}
