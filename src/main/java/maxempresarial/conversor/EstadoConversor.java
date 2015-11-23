package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Estado;
import maxempresarial.repositorio.dao.entidade.EstadoDAO;

@FacesConverter(forClass = Estado.class)
public class EstadoConversor implements Converter {

	@Inject
	private EstadoDAO estadoDAO;

	@Override
	public Estado getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Estado retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.estadoDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object estado) {
		String retorno = "";

		if (estado != null) {
			Long pk = ((Estado) estado).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
