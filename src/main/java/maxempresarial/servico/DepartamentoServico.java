package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Departamento;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.DepartamentoDAO;

public class DepartamentoServico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DepartamentoDAO departamentoDAO;
	
	@Transactional
	public Departamento salvar(Departamento departamento) throws ExcecaoRN, ExcecaoDAO {		
		return this.departamentoDAO.guardar(departamento);
	}

	@Transactional
	public void excluir(Departamento departamento) throws ExcecaoRN, ExcecaoDAO {
		this.departamentoDAO.remover(departamento, departamento.getPk());
	}

}
