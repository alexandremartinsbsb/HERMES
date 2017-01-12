package maxempresarial.repositorio.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import maxempresarial.modelo.Perfil;
import maxempresarial.modelo.enumTipo.Roles;
import maxempresarial.repositorio.filtro.PerfilFiltro;

public class PerfilPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager gerenciadorEntidade;

	@Inject
	public PerfilPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public List<Perfil> filtrados(PerfilFiltro filtro, String... campos) {
		From<?, ?> orderByFromEntity = null;
		List<Perfil> listaPerfil = new ArrayList<>();

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

		Root<Perfil> perfilRoot = criteriaQuery.from(Perfil.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, perfilRoot);
		criteriaQuery.multiselect(
				perfilRoot.get("pk"), 
				perfilRoot.get("versao"), 
				perfilRoot.get("role"));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = perfilRoot;

			if (filtro.getPropriedadeParaOrdenacao().contains(".")) {
				nomePropriedadeOrdenacao = nomePropriedadeOrdenacao.substring(filtro.getPropriedadeParaOrdenacao().indexOf(".") + 1);
			}

			if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			} else if (filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			}
		}

		TypedQuery<Object[]> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		query.setFirstResult(filtro.getPrimeiroRegistro());
		query.setMaxResults(filtro.getQuantidadeDeRegistros());

		for (Object obejeto[] : query.getResultList()) {
			Perfil perfil = new Perfil();

			perfil.setPk((Long) obejeto[0]);
			perfil.setVersao((Integer) obejeto[1]);
			perfil.setRole((Roles) obejeto[2]);

			listaPerfil.add(perfil);
		}

		return listaPerfil;
	}

	public int quantidadePerfisFiltrados(PerfilFiltro filtro) {

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Perfil> perfilRoot = criteriaQuery.from(Perfil.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, perfilRoot);
		criteriaQuery.select(builder.count(perfilRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		return query.getSingleResult().intValue();
	}

	private List<Predicate> criarPredicatesParaFiltro(PerfilFiltro filtro, Root<Perfil> perfilRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getRole())) {
			predicates.add(builder.like(builder.lower(perfilRoot.<String>get("role")), "%" + filtro.getRole().toLowerCase() + "%"));
		}

		return predicates;
	}

}
