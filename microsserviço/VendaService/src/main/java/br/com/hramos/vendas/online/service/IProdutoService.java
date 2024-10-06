package br.com.hramos.vendas.online.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hramos.vendas.online.domain.Produto;

@FeignClient(name = "produto", url = "http://localhost:8082/produto")
public interface IProdutoService {

	@GetMapping( value = "/{codigo}", produces = "application/json")
	Produto buscarProduto(@PathVariable("codigo") String codigoProduto);
}
