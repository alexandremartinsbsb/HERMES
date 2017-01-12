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

import maxempresarial.modelo.Pais;
import maxempresarial.repositorio.filtro.PaisFiltro;

public class PaisPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager gerenciadorEntidade;

	@Inject
	public PaisPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public List<Pais> filtrados(PaisFiltro filtro, String... campos) {
		From<?, ?> orderByFromEntity = null;
		List<Pais> listaPais = new ArrayList<>();

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

		Root<Pais> paisRoot = criteriaQuery.from(Pais.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, paisRoot);
		criteriaQuery.multiselect(
				paisRoot.get("pk"), 
				paisRoot.get("versao"), 
				paisRoot.get("nome"),
				paisRoot.get("sigla"),
				paisRoot.get("moeda"), 
				paisRoot.get("ddi"));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = paisRoot;

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
			Pais pais = new Pais();

			pais.setPk((Long) obejeto[0]);
			pais.setVersao((Integer) obejeto[1]);
			pais.setNome((String) obejeto[2]);
			pais.setSigla((String) obejeto[3]);
			pais.setMoeda((String) obejeto[4]);
			pais.setDdi((String) obejeto[5]);

			listaPais.add(pais);
		}

		return listaPais;
	}

	public int quantidadePaisesFiltrados(PaisFiltro filtro) {

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Pais> paisRoot = criteriaQuery.from(Pais.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, paisRoot);
		criteriaQuery.select(builder.count(paisRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		return query.getSingleResult().intValue();
	}

	private List<Predicate> criarPredicatesParaFiltro(PaisFiltro filtro, Root<Pais> paisRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(paisRoot.<String>get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		return predicates;
	}

}
