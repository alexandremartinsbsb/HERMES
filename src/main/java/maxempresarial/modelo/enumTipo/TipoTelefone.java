package maxempresarial.modelo.enumTipo;

public enum TipoTelefone {

	COMERCIAL("Comercial"), PESSOAL("Pessoal"), OUTROS("Outros");

	private String descricao;

	TipoTelefone(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
