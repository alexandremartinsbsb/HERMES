package maxempresarial.repositorio;

import java.io.Serializable;

import maxempresarial.modelo.Usuario;
import maxempresarial.repositorio.dao.GenericoDAO;
import maxempresarial.repositorio.dao.entidade.UsuarioDAO;

public class UsuarioRepositorio extends GenericoDAO<Usuario, Long> implements UsuarioDAO, Serializable {

	private static final long serialVersionUID = 1L;

}
