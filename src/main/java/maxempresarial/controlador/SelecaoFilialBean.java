package maxempresarial.controlador;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.filtro.FilialFiltro;
import maxempresarial.repositorio.pesquisa.FilialPesquisa;

@Named
@ViewScoped
public class SelecaoFilialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FilialPesquisa filialPesquisa;

	private FilialFiltro filtro;
	private LazyDataModel<Filial> lazyFiliais;

	public SelecaoFilialBean() {
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

	public void selecionar(Filial filial) {
		RequestContext.getCurrentInstance().closeDialog(filial);
	}

	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", 870);
		opcoes.put("contentHeight", 670);

		RequestContext.getCurrentInstance().openDialog("/dialogos/selecaoFilial", opcoes, null);
	}

	public FilialFiltro getFiltro() {
		return filtro;
	}

	public LazyDataModel<Filial> getLazyFiliais() {
		return lazyFiliais;
	}

}
