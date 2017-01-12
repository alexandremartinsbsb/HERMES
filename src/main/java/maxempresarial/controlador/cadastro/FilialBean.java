package maxempresarial.controlador.cadastro;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import maxempresarial.modelo.Cidade;
import maxempresarial.modelo.Contato;
import maxempresarial.modelo.Empresa;
import maxempresarial.modelo.Endereco;
import maxempresarial.modelo.Estado;
import maxempresarial.modelo.Filial;
import maxempresarial.modelo.Pais;
import maxempresarial.modelo.Telefone;
import maxempresarial.modelo.enumTipo.RamoAtividade;
import maxempresarial.modelo.enumTipo.TipoTelefone;
import maxempresarial.modelo.enumTipo.TiposSimplesNacional;
import maxempresarial.modelo.enumTipo.TiposSimplesNacionalIssqn;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.CidadeFiltro;
import maxempresarial.repositorio.filtro.EstadoFiltro;
import maxempresarial.repositorio.filtro.PaisFiltro;
import maxempresarial.repositorio.pesquisa.CidadePesquisa;
import maxempresarial.repositorio.pesquisa.EstadoPesquisa;
import maxempresarial.repositorio.pesquisa.PaisPesquisa;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.servico.FilialServico;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FilialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FilialServico filialServico;

	@Inject
	private EstadoPesquisa estadoPesquisa;

	@Inject
	private CidadePesquisa cidadePesquisa;

	@Inject
	private PaisPesquisa paisPesquisa;

	private Filial filial;
	private Endereco endereco;
	private Contato contato;
	private Telefone telefone;
	private UploadedFile uploadArquivo;

	private List<Estado> estadosEndereco;
	private List<Estado> estadosTelefone;
	private List<Cidade> cidades;
	private List<Pais> paises = new ArrayList<>();

	public FilialBean() {
		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		if (this.filial == null) {
			this.limpar();
		}
		this.paises = this.paisPesquisa.filtrados(new PaisFiltro());
	}

	private void limpar() {
		this.filial = new Filial();
		this.endereco = new Endereco();
		this.estadosEndereco = new ArrayList<>();
		this.estadosTelefone = new ArrayList<>();
		this.cidades = new ArrayList<>();
		this.contato = new Contato();
		this.telefone = new Telefone();
	}

	public void adicionarEndereco() {
		this.endereco = new Endereco();
		this.estadosEndereco = new ArrayList<>();
		this.cidades = new ArrayList<>();
	}

	public void adicionarContato() {
		this.contato = new Contato();
	}

	public void adicionarTelefone() {
		this.telefone = new Telefone();
		this.estadosTelefone = new ArrayList<>();
	}

	public void salvarFilial() {
		try {
			if (this.uploadArquivo != null) {
				this.filial.setLogo(this.uploadArquivo.getContents());
			}
			this.filial = this.filialServico.salvar(this.filial);
			this.limpar();

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("salvo_falha_mensagem"), edao.getMessage());
		}
	}

	public void inserirEndereco() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		if (this.endereco.getPk() == null) {
			this.filial.getEnderecos().add(this.endereco);
			adicionado = true;
			this.endereco = new Endereco();
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		} else if (this.endereco != null) {
			adicionado = true;
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		}
		context.addCallbackParam("adicionado", adicionado);
	}

	public void inserirContato() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		if (this.contato.getPk() == null) {
			this.filial.getContatos().add(this.contato);
			adicionado = true;
			this.contato = new Contato();
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		} else if (this.contato != null) {
			adicionado = true;
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		}
		context.addCallbackParam("adicionado", adicionado);
	}

	public void inserirTelefone() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		if (this.telefone.getPk() == null) {
			this.filial.getTelefones().add(this.telefone);
			adicionado = true;
			this.telefone = new Telefone();
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		} else if (this.telefone != null) {
			adicionado = true;
			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("inserido_corretamente_detalhes"));
		}
		context.addCallbackParam("adicionado", adicionado);
	}

	public void abrirDialogoCidade() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);

		RequestContext.getCurrentInstance().openDialog("/dialogos/selecaoCidade", opcoes, null);
	}

	public void selecionarCidade(Cidade cidade) {
		RequestContext.getCurrentInstance().closeDialog(cidade);
	}

	public void cidadeSelecionada(SelectEvent event) {
		this.telefone.setDdd((Cidade) event.getObject());
	}

	public void excluirEndereco() {
		if (this.endereco != null) {
			this.filial.getEnderecos().remove(this.endereco);
		}
	}

	public void excluirContato() {
		if (this.contato != null) {
			this.filial.getContatos().remove(this.contato);
		}
	}

	public void excluirTelefone() {
		if (this.telefone != null) {
			this.filial.getTelefones().remove(this.telefone);
		}
	}

	public void empresaSelecionada(SelectEvent event) {
		this.filial.setEmpresa((Empresa) event.getObject());
	}

	public boolean isEditandoFilial() {
		return this.filial.getPk() != null;
	}

	public boolean isFaseRenderizacao() {
		if (FacesContext.getCurrentInstance().getCurrentPhaseId().getName().equals("RENDER_RESPONSE")) {
			return true;
		} else {
			return false;
		}

	}

	public TiposSimplesNacional[] getTiposSimplesNacional() {
		return TiposSimplesNacional.values();
	}

	public TiposSimplesNacionalIssqn[] getTiposSimplesNacionalIssqn() {
		return TiposSimplesNacionalIssqn.values();
	}

	public TipoTelefone[] getTipoTelefone() {
		return TipoTelefone.values();
	}

	public RamoAtividade[] getRamo() {
		return RamoAtividade.values();
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Estado> getEstadosEndereco() {
		this.estadosEndereco.clear();
		if (this.endereco.getPais() != null) {
			EstadoFiltro filtro = new EstadoFiltro();
			filtro.setPais(this.telefone.getDdi());
			return estadosEndereco = this.estadoPesquisa.filtrados(filtro);
		}
		return null;
	}

	public List<Estado> getEstadosTelefone() {
		this.estadosTelefone.clear();
		if (this.telefone.getDdi() != null) {
			EstadoFiltro filtro = new EstadoFiltro();
			filtro.setPais(this.telefone.getDdi());
			return estadosTelefone = this.estadoPesquisa.filtrados(filtro);
		}
		return null;
	}

	public List<Cidade> getCidades() {
		this.cidades.clear();
		if (this.endereco.getEstado() != null) {
			CidadeFiltro filtro = new CidadeFiltro();
			filtro.setEstado(this.endereco.getEstado());
			return cidades = this.cidadePesquisa.filtrados(filtro);
		}
		return null;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public UploadedFile getUploadArquivo() {
		return uploadArquivo;
	}

	public void setUploadArquivo(UploadedFile uploadArquivo) {
		this.uploadArquivo = uploadArquivo;
	}

	public BufferedImage getLogo() throws IOException {
		if (this.filial.getLogo() != null) {
			return ImageIO.read(new ByteArrayInputStream(this.filial.getLogo()));
		}
		return null;
	}

	@NotBlank(message = "Empresa não pode esta vazia.")
	public String getNomeEmpresa() {
		return this.filial.getEmpresa() == null ? null : this.filial.getEmpresa().getNomeFantasia();
	}

	// metodo para burlar erro do JSF
	public void setNomeEmpresa(String nome) {
	}

	@NotBlank(message = "DDD não pode esta vazia.")
	public String getDdd() {
		return this.telefone.getDdd() == null ? null : this.telefone.getDdd().getDdd();
	}

	// metodo para burlar erro do JSF
	public void setDdd(String nome) {
	}

}
