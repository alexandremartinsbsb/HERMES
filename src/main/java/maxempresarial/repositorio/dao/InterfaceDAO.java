package maxempresarial.repositorio.dao;

import java.io.Serializable;

public interface InterfaceDAO<T, PK extends Serializable> {

	public T porPk(PK pk);

	public T guardar(T entidade) throws ExcecaoDAO;

	public void remover(T entidade, PK pk) throws ExcecaoDAO;
}
