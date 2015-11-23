package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Cidade;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.CidadeDAO;

public class CidadeRepositorio extends GenericoDAO<Cidade, Long> implements CidadeDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
