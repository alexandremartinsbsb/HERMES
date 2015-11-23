package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Departamento;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.DepartamentoDAO;

public class DepartamentoRepositorio extends GenericoDAO<Departamento, Long> implements DepartamentoDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
