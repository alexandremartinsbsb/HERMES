package maxempresarial.controlador.cadastro;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import maxempresarial.modelo.Estado;
import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.EstadoFiltro;
import maxempresarial.repositorio.filtro.PaisFiltro;
import maxempresarial.repositorio.pesquisa.EstadoPesquisa;
import maxempresarial.repositorio.pesquisa.PaisPesquisa;
import maxempresarial.servico.EstadoServico;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoServico estadoServico;

	@Inject
	private EstadoPesquisa estadoPesquisa;

	@Inject
	private PaisPesquisa paisPesquisa;

	private Estado estado;
	private UploadedFile uploadArquivo = null;
	private EstadoFiltro filtro;
	private LazyDataModel<Estado> lazyEstados;
	private List<Pais> paises = new ArrayList<>();

	@PostConstruct
	public void inicializar() {
		if (this.estado == null) {
			this.estado = new Estado();
		}
		this.paises = this.paisPesquisa.filtrados(new PaisFiltro());
		this.filtro = new EstadoFiltro();

		this.lazyEstados = new LazyDataModel<Estado>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Estado> load(int primeiroRegistro, int quantidadeRegistros, String propriedadeOrdenacao, SortOrder ascendente,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(primeiroRegistro);
				filtro.setQuantidadeDeRegistros(quantidadeRegistros);
				filtro.setPropriedadeParaOrdenacao(propriedadeOrdenacao);
				filtro.setAscendente(SortOrder.ASCENDING.equals(ascendente));

				this.setRowCount(estadoPesquisa.quantidadeEstadosFiltrados(filtro));

				return estadoPesquisa.filtrados(filtro);
			}

		};
	}

	public void adicionar() {
		this.estado = new Estado();
	}

	public void salvar() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		try {
			if (this.uploadArquivo != null) {
				this.estado.setBandeira(this.uploadArquivo.getContents());
			}
			this.estado = this.estadoServico.salvar(this.estado);
			adicionado = true;
			this.estado = new Estado();

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("salvo_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("salvo_falha_mensagem"), edao.getMessage());
		}
		context.addCallbackParam("adicionado", adicionado);
	}

	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloDaCelula = planilha.createCellStyle();
		Font fonteDoCabecalho = planilha.createFont();

		fonteDoCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteDoCabecalho.setBold(true);
		fonteDoCabecalho.setFontHeightInPoints((short) 13);

		estiloDaCelula.setFont(fonteDoCabecalho);
		estiloDaCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloDaCelula.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloDaCelula);
		}

	}

	public void excluir() {
		try {
			this.estadoServico.excluir(this.estado);

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("exclusao_falha_mensagem"), edao.getMessage());
		}
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public EstadoFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Estado> getLazyEstados() {
		return lazyEstados;
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
	
	public BufferedImage getBandeira() throws IOException {
		if (this.estado.getBandeira() != null) {
			return ImageIO.read(new ByteArrayInputStream(this.estado.getBandeira()));
		}
		return null;
	}

}
