package br.com.hramos.dao.generic;

import br.com.hramos.domain.Persistente;
import br.com.hramos.exceptions.DAOException;
import br.com.hramos.exceptions.MaisDeUmRegistroException;
import br.com.hramos.exceptions.TableException;
import br.com.hramos.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistente, E extends Serializable> {

    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public void excluir(T entity) throws DAOException;

    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException;

    public Collection<T> buscarTodos() throws DAOException;
}