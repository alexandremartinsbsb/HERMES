package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.EmpresaDAO;

/**
 * Classe de regra de negocio para CRUD da EMPRESA
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */

public class EmpresaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaDAO empresaDAO;

	@Transactional
	public Empresa salvar(Empresa empresa) throws ExcecaoRN, ExcecaoDAO {
		return this.empresaDAO.guardar(empresa);
	}

	@Transactional
	public void excluir(Empresa empresa) throws ExcecaoRN, ExcecaoDAO {
		this.empresaDAO.remover(empresa, empresa.getPk());
	}

}
