package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.dao.entidade.FilialDAO;

@FacesConverter(forClass = Filial.class)
public class FilialConversor implements Converter {

	@Inject
	private FilialDAO filialDAO;

	@Override
	public Filial getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Filial retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.filialDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object filial) {
		String retorno = "";
		if (filial != null) {
			Long pk = ((Filial) filial).getPk();
			retorno = (pk == null ? null : pk.toString());
		}
		return retorno;
	}

}
