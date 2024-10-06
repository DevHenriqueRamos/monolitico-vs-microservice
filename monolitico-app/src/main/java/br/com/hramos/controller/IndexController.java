package br.com.hramos.controller;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = -784519597996507487L;

	public void redirectCliente() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(externalContext.getRequestContextPath() + "/cliente/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void redirectProduto() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(externalContext.getRequestContextPath() + "/produto/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void redirectVenda() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(externalContext.getRequestContextPath() + "/venda/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
