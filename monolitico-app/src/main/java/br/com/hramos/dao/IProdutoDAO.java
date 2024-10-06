package br.com.hramos.dao;

import br.com.hramos.dao.generic.IGenericDAO;
import br.com.hramos.domain.Produto;

import java.util.List;

public interface IProdutoDAO extends IGenericDAO<Produto, String> {

	List<Produto> filtrarProdutos(String query);

}
