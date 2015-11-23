package maxempresarial.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {

	public static String getMensagemInternacionalizacao(String chave) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		return contexto.getApplication().getResourceBundle(contexto, "msg").getString(chave);
	}

	public static void mensagemErro(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, detalhe));
	}

	public static void mensagemInformacao(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, detalhe));
	}

	public static void mensagemAviso(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, detalhe));
	}

}