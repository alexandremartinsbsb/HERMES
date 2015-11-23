package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Usuario;
import maxempresarial.repositorio.dao.entidade.UsuarioDAO;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConversor implements Converter {

	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public Usuario getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Usuario retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.usuarioDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object usuario) {
		String retorno = "";
		if (usuario != null) {
			Long pk = ((Usuario) usuario).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
