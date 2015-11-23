package maxempresarial.repositorio.filtro;

import java.io.Serializable;

public class FilialFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeFantasia;

	private int primeiroRegistro;
	private int quantidadeDeRegistros;
	private String propriedadeParaOrdenacao;
	private boolean ascendente;

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public int getQuantidadeDeRegistros() {
		return quantidadeDeRegistros;
	}

	public void setQuantidadeDeRegistros(int quantidadeDeRegistros) {
		this.quantidadeDeRegistros = quantidadeDeRegistros;
	}

	public String getPropriedadeParaOrdenacao() {
		return propriedadeParaOrdenacao;
	}

	public void setPropriedadeParaOrdenacao(String propriedadeParaOrdenacao) {
		this.propriedadeParaOrdenacao = propriedadeParaOrdenacao;
	}

	public boolean isAscendente() {
		return ascendente;
	}

	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}

}
