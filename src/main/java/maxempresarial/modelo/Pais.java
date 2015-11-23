package maxempresarial.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cadastro_pais")
public class Pais implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@Version
	@Column(name = "pais_integer_versao")
	private Integer versao;

	@NotNull(message = "Por favor, informe o nome do país.")
	@Size(max = 60, message = "Mínimo de 3 e máximo de 40 caracteres para o nome do país.", min = 3)
	@Column(name = "pais_string_nome", length = 60, nullable = false)
	private String nome;

	@Size(max = 5, message = "Máximo de 5 caracteres para a sigla do país.")
	@Column(name = "pais_string_sigla", length = 5)
	private String sigla;

	@Column(name = "pais_byte_bandeira")
	private byte[] bandeira;

	@Size(max = 20, message = "Máximo de 20 caracteres para a moeda do país.")
	@Column(name = "pais_string_moeda", length = 20)
	private String moeda;

	@Size(max = 5, message = "Máximo de 5 caracteres para o DDI do país")
	@Column(name = "pais_string_ddi", length = 5)
	private String ddi;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public byte[] getBandeira() {
		return bandeira;
	}

	public void setBandeira(byte[] bandeira) {
		this.bandeira = bandeira;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public String getDdi() {
		return ddi;
	}

	public void setDdi(String ddi) {
		this.ddi = ddi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
