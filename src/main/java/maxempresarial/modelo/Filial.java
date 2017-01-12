package maxempresarial.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CNPJ;

import maxempresarial.modelo.enumTipo.RamoAtividade;
import maxempresarial.modelo.enumTipo.TiposSimplesNacional;
import maxempresarial.modelo.enumTipo.TiposSimplesNacionalIssqn;

@Entity
@Table(name = "cadastro_filial", schema = "desenvolvimento")
public class Filial implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk;

	@ManyToOne
	@NotNull(message = "Empresa não pode esta vazia.")
	@JoinColumn(name = "filial_fk_empresa", nullable = false)
	private Empresa empresa;

	@Version
	@Column(name = "filial_integer_versao")
	private Integer versao;

	@NotNull(message = "Por favor, informe a razão social.")
	@Size(max = 100, min = 3, message = "Por favor, informe a razão social completa.")
	@Column(name = "filial_string_razaoSocial", length = 100, nullable = false)
	private String razaoSocial;

	@NotNull(message = "Por favor, informe o nome fantasia.")
	@Size(max = 100, min = 3, message = "Por favor, informe o nome fantasia completo.")
	@Column(name = "filial_string_nomeFantasia", length = 100, nullable = false)
	private String nomeFantasia;

	@CNPJ
	@NotNull(message = "Por favor, informe um CNPJ.")
	@Column(name = "filial_string_cnpj", length = 20, unique = true, nullable = false)
	private String cnpj;

	@Size(max = 15)
	@Column(name = "filial_string_inscricaoEstadual", length = 15)
	private String inscricaoEstadual;

	@Size(max = 15)
	@Column(name = "filial_string_inscricaoMunicipal", length = 15)
	private String inscricaoMunicipal;

	@URL
	@Column(name = "filial_string_site")
	private String site;

	@NotNull(message = "Por favor, informe o nome do responsavel.")
	@Size(max = 40, min = 3, message = "Por favor, informe o nome completo do responsavel.")
	@Column(name = "filial_string_responsavel", length = 40, nullable = false)
	private String responsavel;

	@Enumerated(EnumType.STRING)
	@Column(name = "filial_enum_ramo")
	private RamoAtividade ramo;

	@Enumerated(EnumType.STRING)
	@Column(name = "filial_enum_crt")
	private TiposSimplesNacional crt;

	@Enumerated(EnumType.STRING)
	@Column(name = "filial_enum_crtissqn")
	private TiposSimplesNacionalIssqn crtIssqn;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "endereco_filial", joinColumns = { @JoinColumn(name = "pk_filial", referencedColumnName = "pk") }, inverseJoinColumns = {
			@JoinColumn(name = "pk_endereco", referencedColumnName = "pk") })
	private List<Endereco> enderecos = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "contato_filial", joinColumns = { @JoinColumn(name = "pk_filial", referencedColumnName = "pk") }, inverseJoinColumns = {
			@JoinColumn(name = "pk_contato", referencedColumnName = "pk") })
	private List<Contato> contatos = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "telefone_filial", joinColumns = { @JoinColumn(name = "pk_filial", referencedColumnName = "pk") }, inverseJoinColumns = {
			@JoinColumn(name = "pk_telefone", referencedColumnName = "pk") })
	private List<Telefone> telefones = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "filial_fk_auditoria")
	private Auditoria auditoria;

	@Column(name = "filial_byte_logo")
	private byte[] logo;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public RamoAtividade getRamo() {
		return ramo;
	}

	public void setRamo(RamoAtividade ramo) {
		this.ramo = ramo;
	}

	public TiposSimplesNacional getCrt() {
		return crt;
	}

	public void setCrt(TiposSimplesNacional crt) {
		this.crt = crt;
	}

	public TiposSimplesNacionalIssqn getCrtIssqn() {
		return crtIssqn;
	}

	public void setCrtIssqn(TiposSimplesNacionalIssqn crtIssqn) {
		this.crtIssqn = crtIssqn;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
		Filial other = (Filial) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
