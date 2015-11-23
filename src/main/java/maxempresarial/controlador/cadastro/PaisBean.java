package maxempresarial.controlador.cadastro;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
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
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.PaisFiltro;
import maxempresarial.repositorio.pesquisa.PaisPesquisa;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.servico.PaisServico;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PaisBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PaisServico paisServico;

	@Inject
	private PaisPesquisa paisPesquisa;

	private Pais pais;
	private UploadedFile uploadArquivo = null;
	private PaisFiltro filtro;
	private LazyDataModel<Pais> lazyPaises;

	@PostConstruct
	public void inicializar() {
		this.filtro = new PaisFiltro();

		this.lazyPaises = new LazyDataModel<Pais>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Pais> load(int primeiroRegistro, int quantidadeRegistros, String propriedadeOrdenacao, SortOrder ascendente,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(primeiroRegistro);
				filtro.setQuantidadeDeRegistros(quantidadeRegistros);
				filtro.setPropriedadeParaOrdenacao(propriedadeOrdenacao);
				filtro.setAscendente(SortOrder.ASCENDING.equals(ascendente));

				this.setRowCount(paisPesquisa.quantidadePaisesFiltrados(filtro));

				return paisPesquisa.filtrados(filtro);
			}

		};
	}

	public void adicionar() {
		this.pais = new Pais();
	}

	public void salvarPais() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		try {
			if (this.uploadArquivo != null) {
				this.pais.setBandeira(this.uploadArquivo.getContents());
			}
			this.pais = this.paisServico.salvar(this.pais);
			adicionado = true;
			this.pais = new Pais();

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
		estiloDaCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloDaCelula);
		}

	}

	public void excluir() {
		try {
			this.paisServico.excluir(this.pais);

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("exclusao_falha_mensagem"), edao.getMessage());
		}
	}

	public Pais getPais() {
		return this.pais == null ? null : this.pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public PaisFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Pais> getLazyPaises() {
		return lazyPaises;
	}

	public UploadedFile getUploadArquivo() {
		return uploadArquivo;
	}

	public void setUploadArquivo(UploadedFile uploadArquivo) {
		this.uploadArquivo = uploadArquivo;
	}

	public BufferedImage getBandeira() throws IOException {
		if (this.pais.getBandeira() != null) {
			return ImageIO.read(new ByteArrayInputStream(this.pais.getBandeira()));
		}
		return null;
	}

}
