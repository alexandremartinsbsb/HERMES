package maxempresarial.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import maxempresarial.modelo.Usuario;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.UsuarioDAO;

public class UsuarioServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Transactional
	public Usuario salvar(Usuario usuario) throws ExcecaoRN, ExcecaoDAO {
		if (usuario.getPk() == null && usuario.getSenha() == null) {
			throw new ExcecaoRN("excecao_rn_cadastro_usuario_salvar");
		}
		return this.usuarioDAO.guardar(usuario);
	}

	@Transactional
	public void excluir(Usuario usuario) throws ExcecaoRN, ExcecaoDAO {
		this.usuarioDAO.remover(usuario, usuario.getPk());
	}

}
