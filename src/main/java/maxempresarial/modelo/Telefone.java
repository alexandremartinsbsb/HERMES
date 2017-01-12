package maxempresarial.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import maxempresarial.modelo.enumTipo.TipoTelefone;

@Entity
@Table(name = "cadastro_telefone", schema = "desenvolvimento")
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@Version
	@Column(name = "telefone_integer_versao")
	private Integer versao;

	@ManyToOne
	@JoinColumn(name = "telefone_fk_ddi")
	private Pais ddi;

	@ManyToOne
	@NotNull(message = "Informe o DDD.")
	@JoinColumn(name = "telefone_fk_ddd", nullable = false)
	private Cidade ddd;

	@Size(max = 10, min = 10, message = "Informe o telefone completo.")
	@NotNull(message = "Informe o telefone.")
	@Column(name = "telefone_string_telefone", nullable = false)
	private String telefone;

	@Enumerated(EnumType.STRING)
	@Column(name = "telefone_enum_tipo")
	private TipoTelefone tipo;

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

	public Pais getDdi() {
		return ddi;
	}

	public void setDdi(Pais ddi) {
		this.ddi = ddi;
	}

	public Cidade getDdd() {
		return ddd;
	}

	public void setDdd(Cidade ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
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
		Telefone other = (Telefone) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
