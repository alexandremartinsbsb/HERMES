package maxempresarial.modelo.enumTipo;

public enum RamoAtividade {

	ACESSORIO("Acessorio"), ALIMENTACAO("Alimentação"), BEBIDA("Bebida"), CALCADO("Calçado"), COMBUSTIVEL("Combustivel"), COURO("Couro"), EDUCACAO("Educação"), FERRAGEM("Ferragem"), GRAFICA(
			"Grafica"), LAZER("lazer"), MECANICA("Mecanica"), METALURGIA("Metalurgia"), MOBILIARIO("Mobiliario"), ROUPA("Roupa"), SAUDE("Saúde"), TECIDO("Tecido"), TRANSPORTE(
					"Transporte"), TURISMO("Turismo"), VEICULO("Veiculo"), VESTUARIO("Vestuario");

	private String descricao;

	RamoAtividade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}