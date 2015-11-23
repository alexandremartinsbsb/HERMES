package maxempresarial.modelo.enumTipo;

public enum TiposSimplesNacionalIssqn {

	MICRO_EMPRESA_MUNICIPAL("Micro Empresa Municipal"), ESTIMATIVA("Estimativa"), SOCIEDADE_PROFISSIONAIS("Sociedade de profissionais"), COOPERATIVA("Cooperativa"), MEI(
			"MEI - Simples nacional"), ME_EPP("ME EPP - Simples nacional");

	private String descricao;

	TiposSimplesNacionalIssqn(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
