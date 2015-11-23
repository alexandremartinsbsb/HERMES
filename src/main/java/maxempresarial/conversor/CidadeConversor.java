package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Cidade;
import maxempresarial.repositorio.dao.entidade.CidadeDAO;

@FacesConverter(forClass = Cidade.class)
public class CidadeConversor implements Converter {

	@Inject
	private CidadeDAO cidadeDAO;

	@Override
	public Cidade getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Cidade retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.cidadeDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object cidade) {
		String retorno = "";

		if (cidade != null) {
			Long pk = ((Cidade) cidade).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
