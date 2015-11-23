package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Estado;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.EstadoDAO;

public class EstadoRepositorio extends GenericoDAO<Estado, Long> implements EstadoDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
