package maxempresarial.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "cadastro_usuario", schema = "desenvolvimento")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@Version
	@Column(name = "usuario_integer_versao")
	private Integer versao;

	@ManyToOne
	@NotNull(message = "A filial não pode ser vazia.")
	@JoinColumn(name = "usuario_fk_filial", nullable = false)
	private Filial filial;

	@ManyToOne
	@JoinColumn(name = "usuario_fk_departamento")
	private Departamento departamento;

	@NotNull(message = "Por favor, informe o nome do usuário.")
	@Size(max = 60, min = 3, message = "Por favor, informe o nome completo do usuário.")
	@Column(name = "usuario_string_nome", length = 60, nullable = false)
	private String nome;

	@Size(max = 40, message = "Máximo de 40 caracteres para o nome do cargo.")
	@Column(name = "usuario_string_cargo", length = 40)
	private String cargo;

	@NotNull(message = "Por favor, informe o login do usuário.")
	@Size(max = 20, min = 6, message = "Mínimo de 6 e máximo de 20 caracteres para o login do usuário.")
	@Column(name = "usuario_string_login", length = 20, nullable = false)
	private String login;

	@Size(max = 20, message = "Máximo de 20 caracteres para a senha do usuário.")
	@Column(name = "usuario_string_senha", length = 20)
	private String senha;

	@Email(message = "E-mail informado não esta no formato correto. Ex: exemplo@maxempresarial.com.br")
	@NotNull(message = "Por favor, informe o e-mail do usuário.")
	@Column(name = "usuario_string_email", nullable = false)
	private String email;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_fk_auditoria")
	private Auditoria auditoria;

	@Column(name = "usuario_boolean_situacao")
	private Boolean situacao = new Boolean(true);

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "perfil_usuario", joinColumns = { @JoinColumn(name = "pk_usuario", referencedColumnName = "pk") }, inverseJoinColumns = {
			@JoinColumn(name = "pk_perfil", referencedColumnName = "pk") })
	private List<Perfil> perfis = new ArrayList<>();

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

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public List<Perfil> getEnderecos() {
		return perfis;
	}

	public void setEnderecos(List<Perfil> enderecos) {
		this.perfis = enderecos;
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
		Usuario other = (Usuario) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
