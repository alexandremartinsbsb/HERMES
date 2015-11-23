package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Estado;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.EstadoDAO;

public class EstadoServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoDAO estadoDAO;

	@Transactional
	public Estado salvar(Estado estado) throws ExcecaoRN, ExcecaoDAO {
		return this.estadoDAO.guardar(estado);
	}

	@Transactional
	public void excluir(Estado estado) throws ExcecaoRN, ExcecaoDAO {
		this.estadoDAO.remover(estado, estado.getPk());
	}

}
