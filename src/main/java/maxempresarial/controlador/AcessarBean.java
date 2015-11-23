package maxempresarial.controlador;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import maxempresarial.util.jsf.FacesUtil;

@Named
@SessionScoped
public class AcessarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext contexto;

	@Inject
	private HttpServletRequest requisicao;

	@Inject
	private HttpServletResponse resposta;

	private String login;

	public void inicializar() {
		if ("true".equals(this.requisicao.getParameter("invalid"))) {
			FacesUtil.mensagemAviso("ATENÇÃO!", "Login ou Senha inválida.");
		}
	}

	public void acessar() throws ServletException, IOException {
		RequestDispatcher dispatcher = this.requisicao.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(this.requisicao, this.resposta);

		this.contexto.responseComplete();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
