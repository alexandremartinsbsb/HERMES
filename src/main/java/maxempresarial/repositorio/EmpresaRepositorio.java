package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.EmpresaDAO;

/**
 * Classe para pessistir o objeto EMPRESA no banco de dados (DAO);
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */
public class EmpresaRepositorio extends GenericoDAO<Empresa, Long> implements EmpresaDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
