package maxempresarial.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cadastro_estado")
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@Version
	@Column(name = "estado_integer_versao")
	private Integer versao;

	@NotNull(message = "Por favor, informe o nome do estado.")
	@Size(max = 40, min = 3, message = "Mínimo de 3 e máximo de 40 caracteres para o nome do estado.")
	@Column(name = "estado_string_nome", length = 40, nullable = false)
	private String nome;

	@Column(name = "estado_string_mascarainscricaoestadual", length = 40)
	private String mascaraInscricaoEstadual;

	@NotNull(message = "Por favor, informe a sigla do estado.")
	@Size(max = 5, message = "Máximo de 5 caracteres para a sigla do estado.")
	@Column(name = "estado_string_sigla", length = 5, nullable = false)
	private String sigla;

	@Column(name = "estado_byte_bandeira")
	private byte[] bandeira;

	@ManyToOne
	@NotNull(message = "Informe o país.")
	@JoinColumn(name = "estado_fk_pais", nullable = false)
	private Pais pais;

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

	public String getMascaraInscricaoEstadual() {
		return mascaraInscricaoEstadual;
	}

	public void setMascaraInscricaoEstadual(String mascaraInscricaoEstadual) {
		this.mascaraInscricaoEstadual = mascaraInscricaoEstadual;
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
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
		Estado other = (Estado) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
