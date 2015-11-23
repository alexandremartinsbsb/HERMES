package maxempresarial.repositorio.filtro;

import java.io.Serializable;

public class UsuarioFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private boolean situacao;

	private int primeiroRegistro;
	private int quantidadeDeRegistros;
	private String propriedadeParaOrdenacao;
	private boolean ascendente;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
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
