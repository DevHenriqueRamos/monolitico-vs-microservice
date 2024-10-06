package br.com.hramos.vendas.online.usecase;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;

import br.com.hramos.vendas.online.domain.Cliente;
import br.com.hramos.vendas.online.repository.IClienteRepository;

@Service
public class CadastroCliente {
	
	private IClienteRepository clienteRepository;
	
	public CadastroCliente(IClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public Cliente cadastrar(@Valid Cliente cliente) {
		return this.clienteRepository.insert(cliente);
	}

	public Cliente atualizar(@Valid Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}

	public void remover(String id) {
		this.clienteRepository.deleteById(id);
	}

}
