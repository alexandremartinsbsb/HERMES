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
@Table(name = "cadastro_cidade", schema = "desenvolvimento")
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@Version
	@Column(name = "cidade_integer_versao")
	private Integer versao;

	@Size(max = 5, message = "Máximo de 5 caracteres para o DDD.")
	@Column(name = "cidade_string_ddd", length = 5)
	private String ddd;

	@NotNull(message = "Por favor, informe o nome da cidade")
	@Size(max = 40, min = 3, message = "Mínimo de 3 e máximo de 40 caracteres para o nome da cidade")
	@Column(name = "cidade_string_nome", length = 40, nullable = false)
	private String nome;

	@Column(name = "cidade_byte_bandeira")
	private byte[] bandeira;

	@ManyToOne
	@NotNull(message = "Informe o estado.")
	@JoinColumn(name = "cidade_fk_estado", nullable = false)
	private Estado estado;

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

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getBandeira() {
		return bandeira;
	}

	public void setBandeira(byte[] bandeira) {
		this.bandeira = bandeira;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
