package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.PaisDAO;

public class PaisServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PaisDAO paisDAO;

	@Transactional
	public Pais salvar(Pais pais) throws ExcecaoRN, ExcecaoDAO {		
		return this.paisDAO.guardar(pais);
	}

	@Transactional
	public void excluir(Pais pais) throws ExcecaoRN, ExcecaoDAO {
		this.paisDAO.remover(pais, pais.getPk());
	}

}
