package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.FilialDAO;

public class FilialRepositorio extends GenericoDAO<Filial, Long> implements FilialDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
