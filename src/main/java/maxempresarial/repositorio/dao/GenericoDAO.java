package maxempresarial.repositorio.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class GenericoDAO<T, PK extends Serializable> implements InterfaceDAO<T, PK> {

	@Inject
	private EntityManager gerenciadorEntidade;

	private Class<T> classeEntidade;

	@SuppressWarnings("unchecked")
	public GenericoDAO() {
		this.classeEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T guardar(T entidade) throws ExcecaoDAO {
		try {
			entidade = this.gerenciadorEntidade.merge(entidade);
			this.gerenciadorEntidade.flush();
			return entidade;
		} catch (Exception e) {
			throw new ExcecaoDAO(e.getMessage());
		}
	}

	@Override
	public void remover(T entidade, PK pk) throws ExcecaoDAO {
		try {
			entidade = this.gerenciadorEntidade.getReference(classeEntidade, pk);
			this.gerenciadorEntidade.remove(entidade);
			this.gerenciadorEntidade.flush();
		} catch (Exception e) {
			throw new ExcecaoDAO(e.getMessage());
		}

	}

	@Override
	public T porPk(PK pk) {
		return this.gerenciadorEntidade.find(classeEntidade, pk);
	}

}
