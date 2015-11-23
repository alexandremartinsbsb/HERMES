package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.FilialDAO;

public class FilialServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FilialDAO filialDAO;

	@Transactional
	public Filial salvar(Filial filial) throws ExcecaoRN, ExcecaoDAO {
		return this.filialDAO.guardar(filial);
	}

	@Transactional
	public void excluir(Filial filial) throws ExcecaoRN, ExcecaoDAO {
		this.filialDAO.remover(filial, filial.getPk());
	}

}
