package maxempresarial.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "movimentacao_auditoria", schema = "desenvolvimento")
public class Auditoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@OneToOne
	@JoinColumn(name = "auditoria_fk_usuarioincluiu")
	private Usuario usuarioIncluiu;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditoria_datetime_datainclusao")
	private Date dataInclusao;

	@OneToOne
	@JoinColumn(name = "auditoria_fk_usuarioalterou")
	private Usuario usuarioAlterou;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditoria_datetime_dataalteracao")
	private Date dataAlteracao;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Usuario getUsuarioIncluiu() {
		return usuarioIncluiu;
	}

	public void setUsuarioIncluiu(Usuario usuarioIncluiu) {
		this.usuarioIncluiu = usuarioIncluiu;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Usuario getUsuarioAlterou() {
		return usuarioAlterou;
	}

	public void setUsuarioAlterou(Usuario usuarioAlterou) {
		this.usuarioAlterou = usuarioAlterou;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
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
		Auditoria other = (Auditoria) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

	@PrePersist
	@PreUpdate
	public void configuraDataCriacaoAlteracao() {
		this.dataAlteracao = new Date();

		if (this.dataInclusao == null) {
			this.dataInclusao = new Date();
		}
	}

}
