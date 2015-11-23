package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Departamento;
import maxempresarial.repositorio.dao.entidade.DepartamentoDAO;

@FacesConverter(forClass = Departamento.class)
public class DepartamentoConversor implements Converter {

	@Inject
	private DepartamentoDAO departamentoDAO;

	@Override
	public Departamento getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Departamento retorno = null;

		if (valor != null && valor.trim().length() > 0) {
			retorno = this.departamentoDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object departamento) {
		String retorno = "";

		if (departamento != null) {
			Long pk = ((Departamento) departamento).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
