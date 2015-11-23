package maxempresarial.modelo.enumTipo;

public enum Roles {

	ADMINISTRADOR("Administrador"), AUXILIAR("Auxiliar");

	private String descricao;

	Roles(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
