package maxempresarial.repositorio.filtro;

import java.io.Serializable;

/**
 * Classe para controlar o filtro quando pesquisar perfil no sistema;
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */

public class PerfilFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String role;

	private int primeiroRegistro;
	private int quantidadeDeRegistros;
	private String propriedadeParaOrdenacao;
	private boolean ascendente;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
