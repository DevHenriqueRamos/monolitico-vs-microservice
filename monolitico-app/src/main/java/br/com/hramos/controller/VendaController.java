package br.com.hramos.controller;

import br.com.hramos.domain.Produto;
import br.com.hramos.domain.ProdutoQuantidade;
import br.com.hramos.domain.Venda;
import br.com.hramos.services.IClienteService;
import br.com.hramos.services.IProdutoService;
import br.com.hramos.services.IVendaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.time.ZoneId;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = -3508753726177740824L;
	
	private Venda venda;
	
	private Collection<Venda> vendas;
	
	@Inject
	private IVendaService vendaService;
	
	@Inject
	private IClienteService clienteService;
	
	@Inject
	private IProdutoService produtoService;
	
	private Boolean isUpdate;
	
	private LocalDate dataVenda;
	
	private Integer quantidadeProduto;
	
	private Set<ProdutoQuantidade> produtos;
	
	private Produto produtoSelecionado;
	
	private Integer valorTotal;
	
	@PostConstruct
    public void init() {
		try {
			this.isUpdate = false;
			this.venda = new Venda();
			this.produtos = new HashSet<>();
			this.vendas = vendaService.buscarTodos();
			this.valorTotal = 0;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar as vendas"));
		}
	}
	
	public void cancel() {
		try {
			this.isUpdate = false;
			this.venda = new Venda();
			this.produtos = new HashSet<>();
			this.valorTotal = 0;
			this.dataVenda = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
		}
		
    } 
	
	public void edit(Venda venda) {
		try {
			this.isUpdate = true;
			this.venda = this.vendaService.consultarComCollection(venda.getId());
			this.dataVenda = this.venda.getDataVenda().atZone(ZoneId.systemDefault()).toLocalDate();
			this.produtos = this.venda.getProdutos();
			this.venda.recalcularValorTotalVenda();
			this.valorTotal = this.venda.getValorTotal();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar a venda"));
		}
		
    } 
	
	public void delete(Venda venda) {
		try {
			vendaService.cancelarVenda(venda);
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar a venda"));
		}
		
    } 
	
	public void finalizar(Venda venda) {
		try {
			vendaService.finalizarVenda(venda);
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar finalizar a venda"));
		}
		
    } 
	
	public void add() {
		try {
			venda.setDataVenda(dataVenda.atStartOfDay(ZoneId.systemDefault()).toInstant());
			vendaService.cadastrar(venda);
			this.vendas = vendaService.buscarTodos();
			cancel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cadastrar a venda"));
		}
    }
	
	public void update() {
    	try {
    		vendaService.alterar(this.venda);
    		this.vendas = vendaService.buscarTodos();
			cancel();
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Venda atualiada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar a venda"));
		}
        
    }
	
	public void adicionarProduto() {
		Optional<ProdutoQuantidade> prodOp =
				this.venda.getProdutos().stream().filter(prodF -> prodF.getProduto().getCodigo().equals(this.produtoSelecionado.getCodigo())).findFirst();

		if (prodOp.isPresent()) {
			ProdutoQuantidade prod = prodOp.get();
			prod.adicionar(this.quantidadeProduto);
		} else {
			ProdutoQuantidade prod = new ProdutoQuantidade();
			prod.setProduto(this.produtoSelecionado);
			prod.adicionar(this.quantidadeProduto);
			prod.setVenda(this.venda);
			this.venda.getProdutos().add(prod);
		}
		this.venda.recalcularValorTotalVenda();
		this.produtos = this.venda.getProdutos();
		this.valorTotal = this.venda.getValorTotal();
	}
	
	public void removerProduto() {
		Optional<ProdutoQuantidade> prodOp = 
				this.venda.getProdutos().stream().filter(prodF -> prodF.getProduto().getCodigo().equals(this.produtoSelecionado.getCodigo())).findFirst();

		if (prodOp.isPresent()) {
			ProdutoQuantidade prod = prodOp.get();
			prod.remover(this.quantidadeProduto);
			if (prod.getQuantidade() == 0 || prod.getQuantidade() < 0) {
				this.venda.getProdutos().remove(prod);
			}
			this.venda.recalcularValorTotalVenda();
			this.produtos = this.venda.getProdutos();
			this.valorTotal = this.venda.getValorTotal();
		}
		
	}
	
	public void removerProduto(ProdutoQuantidade produto) {
		
		this.venda.getProdutos().remove(produto);
		this.venda.recalcularValorTotalVenda();
		this.produtos = this.venda.getProdutos();
		this.valorTotal = this.venda.getValorTotal();
	}
    
    public void adicionarOuRemoverProduto(ProdutoQuantidade prod) {
    	if (prod.getQuantidade() != this.quantidadeProduto) {
    		int quantidade =  this.quantidadeProduto - prod.getQuantidade();
    		if (quantidade > 0) {
    			prod.adicionar(quantidade);
    		} else {
    			this.produtos.remove(prod);
    		}
    		this.valorTotal = 0;
    		this.produtos.forEach(pro -> {
    			this.valorTotal += pro.getValorTotal();
    		});
    	}
    }

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Collection<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(Collection<Venda> vendas) {
		this.vendas = vendas;
	}

	public IVendaService getVendaService() {
		return vendaService;
	}

	public void setVendaService(IVendaService vendaService) {
		this.vendaService = vendaService;
	}

	public IClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(IClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public IProdutoService getProdutoService() {
		return produtoService;
	}

	public void setProdutoService(IProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public Boolean getUpdate() {
		return isUpdate;
	}

	public void setUpdate(Boolean update) {
		isUpdate = update;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public Integer getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Integer valorTotal) {
		this.valorTotal = valorTotal;
	}
}
