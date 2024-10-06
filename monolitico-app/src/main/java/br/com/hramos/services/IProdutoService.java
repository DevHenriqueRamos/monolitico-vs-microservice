package br.com.hramos.services;

import br.com.hramos.domain.Produto;
import br.com.hramos.services.generic.IGenericService;

import java.util.List;

public interface IProdutoService extends IGenericService<Produto, String> {

	List<Produto> filtrarProdutos(String query);

}
