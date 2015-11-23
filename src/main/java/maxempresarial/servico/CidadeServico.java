package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Cidade;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.CidadeDAO;

public class CidadeServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDAO cidadeDAO;

	@Transactional
	public Cidade salvar(Cidade cidade) throws ExcecaoRN, ExcecaoDAO {
		return this.cidadeDAO.guardar(cidade);
	}

	@Transactional
	public void excluir(Cidade cidade) throws ExcecaoRN, ExcecaoDAO {
		this.cidadeDAO.remover(cidade, cidade.getPk());
	}

}
