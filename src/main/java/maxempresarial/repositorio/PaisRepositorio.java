package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.PaisDAO;

public class PaisRepositorio extends GenericoDAO<Pais, Long> implements PaisDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
