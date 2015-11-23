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
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import maxempresarial.modelo.Cidade;
import maxempresarial.modelo.Estado;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.CidadeFiltro;
import maxempresarial.repositorio.filtro.EstadoFiltro;
import maxempresarial.repositorio.pesquisa.CidadePesquisa;
import maxempresarial.repositorio.pesquisa.EstadoPesquisa;
import maxempresarial.servico.CidadeServico;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeServico cidadeServico;

	@Inject
	private CidadePesquisa cidadePesquisa;

	@Inject
	private EstadoPesquisa estadoPesquisa;

	private Cidade cidade;
	private UploadedFile uploadArquivo = null;
	private CidadeFiltro filtro;
	private LazyDataModel<Cidade> lazyCidades;
	private List<Estado> estados = new ArrayList<>();

	@PostConstruct
	public void inicializar() {
		if (this.cidade == null) {
			this.cidade = new Cidade();
		}
		this.estados = this.estadoPesquisa.filtrados(new EstadoFiltro());
		this.filtro = new CidadeFiltro();

		this.lazyCidades = new LazyDataModel<Cidade>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Cidade> load(int primeiroRegistro, int quantidadeRegistros, String propriedadeOrdenacao, SortOrder ascendente,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(primeiroRegistro);
				filtro.setQuantidadeDeRegistros(quantidadeRegistros);
				filtro.setPropriedadeParaOrdenacao(propriedadeOrdenacao);
				filtro.setAscendente(SortOrder.ASCENDING.equals(ascendente));

				this.setRowCount(cidadePesquisa.quantidadeCidadesFiltrados(filtro));

				return cidadePesquisa.filtrados(filtro);
			}

		};
	}

	public void adicionar() {
		this.cidade = new Cidade();
	}

	public void salvar() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean adicionado = false;

		try {
			if (this.uploadArquivo != null) {
				this.cidade.setBandeira(this.uploadArquivo.getContents());
			}
			this.cidade = this.cidadeServico.salvar(this.cidade);
			adicionado = true;
			this.cidade = new Cidade();

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
			this.cidadeServico.excluir(this.cidade);

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("exclusao_falha_mensagem"), edao.getMessage());
		}
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public CidadeFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Cidade> getLazyCidades() {
		return lazyCidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public UploadedFile getUploadArquivo() {
		return uploadArquivo;
	}

	public void setUploadArquivo(UploadedFile uploadArquivo) {
		this.uploadArquivo = uploadArquivo;
	}

	public BufferedImage getBandeira() throws IOException {
		if (this.cidade.getBandeira() != null) {
			return ImageIO.read(new ByteArrayInputStream(this.cidade.getBandeira()));
		}
		return null;
	}

}
