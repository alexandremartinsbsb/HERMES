package maxempresarial.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.entidade.EmpresaDAO;

/**
 * Classe para converter empresa: objeto para string e string para objeto
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */

@FacesConverter(forClass = Empresa.class)
public class EmpresaConversor implements Converter {

	@Inject
	private EmpresaDAO empresaDAO;

	@Override
	public Empresa getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Empresa retorno = null;

		if(valor != null && valor.trim().length() > 0) {
			retorno = this.empresaDAO.porPk(new Long(valor));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object empresa) {
		String retorno = "";
		if (empresa != null) {
			Long pk = ((Empresa) empresa).getPk();
			retorno = pk == null ? null : pk.toString();
		}
		return retorno;
	}

}
