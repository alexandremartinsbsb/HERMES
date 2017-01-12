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

import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.filtro.EmpresaFiltro;
import maxempresarial.repositorio.pesquisa.EmpresaPesquisa;
import maxempresarial.servico.EmpresaServico;
import maxempresarial.servico.ExcecaoRN;
import maxempresarial.util.jsf.FacesUtil;

/**
 * Classe para controlar a tela de pesquisa da empresa;
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */

@Named
@ViewScoped
public class PesquisaEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaPesquisa pesquisaEmpresa;

	@Inject
	private EmpresaServico empresaServico;

	private EmpresaFiltro filtro;
	private LazyDataModel<Empresa> lazyEmpresas;
	private Empresa empresaSelecionada;
	private StreamedContent logo;

	/**
	 * Método construtor onde instacia o filtro e lazy data;
	 * 
	 * @author Alexandre Martins da Silva
	 * 
	 */
	public PesquisaEmpresaBean() {
		this.filtro = new EmpresaFiltro();

		this.lazyEmpresas = new LazyDataModel<Empresa>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Empresa> load(int primeiroRegistro, int quantidadeRegistros, String propriedadeOrdenacao, SortOrder ascendente,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(primeiroRegistro);
				filtro.setQuantidadeDeRegistros(quantidadeRegistros);
				filtro.setPropriedadeParaOrdenacao(propriedadeOrdenacao);
				filtro.setAscendente(SortOrder.ASCENDING.equals(ascendente));

				this.setRowCount(pesquisaEmpresa.quantidadeEmpresasFiltradas(filtro));

				return pesquisaEmpresa.filtradas(filtro);
			}

		};
	}

	/**
	 * Método para gerar arquivo excel (xls);
	 * 
	 * @author Alexandre Martins da Silva
	 * 
	 * @param documento
	 *            - objeto para gerar o arquivo excel (xls)
	 * 
	 */
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
			this.empresaServico.excluir(this.empresaSelecionada);

			FacesUtil.mensagemInformacao(FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_mensagem"),
					FacesUtil.getMensagemInternacionalizacao("exclusao_corretamente_detalhes"));
		} catch (ExcecaoRN ern) {
			FacesUtil.mensagemAviso(FacesUtil.getMensagemInternacionalizacao("atencao_mensagem"), FacesUtil.getMensagemInternacionalizacao(ern.getMessage()));
		} catch (ExcecaoDAO edao) {
			FacesUtil.mensagemErro(FacesUtil.getMensagemInternacionalizacao("exclusao_falha_mensagem"), edao.getMessage());
		}
	}

	public EmpresaFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Empresa> getLazyEmpresas() {
		return lazyEmpresas;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public StreamedContent getLogo() throws IOException {
		if (this.empresaSelecionada.getLogo() != null && this.empresaSelecionada.getLogo().length > 0) {
			this.logo = new DefaultStreamedContent(new ByteArrayInputStream(this.empresaSelecionada.getLogo()), "image/png");
		}
		return this.logo;
	}
}
