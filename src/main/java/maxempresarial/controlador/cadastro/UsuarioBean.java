package maxempresarial.controlador.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import maxempresarial.modelo.Departamento;
import maxempresarial.modelo.Filial;
import maxempresarial.modelo.Perfil;
import maxempresarial.modelo.Usuario;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.DepartamentoFiltro;
import maxempresarial.repositorio.filtro.PerfilFiltro;
import maxempresarial.repositorio.pesquisa.DepartamentoPesquisa;
import maxempresarial.repositorio.pesquisa.PerfilPesquisa;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.servico.UsuarioServico;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioServico usuarioServico;
	@Inject
	private DepartamentoPesquisa departamentoPesquisa;
	@Inject
	private PerfilPesquisa perfilPesquisa;

	private String senha;
	private Usuario usuario;
	private List<Departamento> departamentos = new ArrayList<>();
	private List<Perfil> perfis = new ArrayList<>();

	public UsuarioBean() {
		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		if (this.usuario == null) {
			this.limpar();
		}
		this.departamentos = this.departamentoPesquisa.filtrados(new DepartamentoFiltro());
		this.perfis = this.perfilPesquisa.filtrados(new PerfilFiltro());
	}

	private void limpar() {
		this.usuario = new Usuario();
	}

	public void salvar() {
		try {
			if (this.senha != null && this.senha.trim().length() > 0) {
				this.usuario.setSenha(this.getSenha());
			}
			this.usuario = this.usuarioServico.salvar(this.usuario);
			this.limpar();

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("salvo_falha_mensagem"),
					FacesUtil.getMensagemInternacionalizacao(edao.getMessage()));
		}
	}

	public void adicionarPerfil() {

	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void filialSelecionada(SelectEvent event) {
		this.usuario.setFilial((Filial) event.getObject());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public boolean isEditando() {
		return this.usuario.getPk() != null;
	}

	public boolean isFaseRenderizacao() {
		if (FacesContext.getCurrentInstance().getCurrentPhaseId().getName().equals("RENDER_RESPONSE")) {
			return true;
		} else {
			return false;
		}

	}

	@NotBlank(message = "Filial não pode esta vazia.")
	public String getNomeFilial() {
		return this.usuario.getFilial() == null ? null : this.usuario.getFilial().getNomeFantasia();
	}

	// metodo para burlar erro do JSF
	public void setNomeFilial(String nome) {
	}

}
