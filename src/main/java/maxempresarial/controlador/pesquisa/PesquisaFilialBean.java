package maxempresarial.controlador.pesquisa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.FilialFiltro;
import maxempresarial.repositorio.pesquisa.FilialPesquisa;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.servico.FilialServico;
import maxempresarial.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFilialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FilialPesquisa filialPesquisa;

	@Inject
	private FilialServico filialServico;

	private FilialFiltro filtro;
	private LazyDataModel<Filial> lazyFiliais;
	private Filial filialSelecionada;
	private StreamedContent logo;

	public PesquisaFilialBean() {
		this.filtro = new FilialFiltro();
		this.lazyFiliais = new LazyDataModel<Filial>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Filial> load(int primeiroRegistro, int quantidadeRegistros, String propriedadeOrdenacao, SortOrder ascendente,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(primeiroRegistro);
				filtro.setQuantidadeDeRegistros(quantidadeRegistros);
				filtro.setPropriedadeParaOrdenacao(propriedadeOrdenacao);
				filtro.setAscendente(SortOrder.ASCENDING.equals(ascendente));

				this.setRowCount(filialPesquisa.quantidadeFiliaisFiltradas(filtro));

				return filialPesquisa.filtradas(filtro);
			}
		};
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
			this.filialServico.excluir(this.filialSelecionada);

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("exclusao_falha_mensagem"), edao.getMessage());
		}
	}

	public FilialFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Filial> getLazyFiliais() {
		return lazyFiliais;
	}

	public Filial getFilialSelecionada() {
		return filialSelecionada;
	}

	public void setFilialSelecionada(Filial filialSelecionada) {
		this.filialSelecionada = filialSelecionada;
	}

	public StreamedContent getLogo() throws IOException {
		if (this.filialSelecionada.getLogo() != null && this.filialSelecionada.getLogo().length > 0) {
			this.logo = new DefaultStreamedContent(new ByteArrayInputStream(this.filialSelecionada.getLogo()), "image/png");
		}
		return this.logo;
	}

}
