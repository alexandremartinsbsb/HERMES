package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.dao.entidade.PaisDAO;

@FacesConverter(forClass = Pais.class)
public class PaisConversor implements Converter {

	@Inject
	private PaisDAO paisDAO;

	@Override
	public Pais getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Pais retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.paisDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object pais) {
		String retorno = "";

		if (pais != null) {
			Long pk = ((Pais) pais).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
