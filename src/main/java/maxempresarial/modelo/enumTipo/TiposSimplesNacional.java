package maxempresarial.modelo.enumTipo;

public enum TiposSimplesNacional {

	SIMPLES_NACIONAL("Simples Nacional"), SIMPLES_NACIONAL_EXCESSO("Simples Nacional - excesso de sublimite da receita bruta"), REGIME_NORMAL("Regime Normal");

	private String descricao;

	TiposSimplesNacional(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
