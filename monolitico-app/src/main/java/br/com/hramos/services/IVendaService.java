package br.com.hramos.services;

import br.com.hramos.dao.generic.IGenericDAO;
import br.com.hramos.domain.Venda;
import br.com.hramos.exceptions.DAOException;
import br.com.hramos.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaService extends IGenericDAO<Venda, Long> {

	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
	
	public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

	public Venda consultarComCollection(Long id);

}
