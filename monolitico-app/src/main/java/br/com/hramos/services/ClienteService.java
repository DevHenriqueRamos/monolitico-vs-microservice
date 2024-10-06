package br.com.hramos.services;

import br.com.hramos.dao.IClienteDAO;
import br.com.hramos.domain.Cliente;
import br.com.hramos.exceptions.DAOException;
import br.com.hramos.exceptions.MaisDeUmRegistroException;
import br.com.hramos.exceptions.TableException;
import br.com.hramos.services.generic.GenericService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
	
	private IClienteDAO clienteDAO;
	
	@Inject
	public ClienteService(IClienteDAO clienteDAO) {
		super(clienteDAO);
		this.clienteDAO = clienteDAO;
	}

	@Override
	public Cliente buscarPorCPF(Long cpf) throws DAOException {
		try {
			return this.dao.consultar(cpf);
		} catch (MaisDeUmRegistroException | TableException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cliente> filtrarClientes(String query) {
		return clienteDAO.filtrarClientes(query);
	}

}
