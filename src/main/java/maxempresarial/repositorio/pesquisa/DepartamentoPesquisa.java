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

import maxempresarial.modelo.Departamento;
import maxempresarial.repositorio.filtro.DepartamentoFiltro;

public class DepartamentoPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager gerenciadorEntidade;

	@Inject
	public DepartamentoPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public List<Departamento> filtrados(DepartamentoFiltro filtro) {
		From<?, ?> orderByFromEntity = null;

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Departamento> criteriaQuery = builder.createQuery(Departamento.class);

		Root<Departamento> departamentoRoot = criteriaQuery.from(Departamento.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, departamentoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = departamentoRoot;

			if (filtro.getPropriedadeParaOrdenacao().contains(".")) {
				nomePropriedadeOrdenacao = nomePropriedadeOrdenacao.substring(filtro.getPropriedadeParaOrdenacao().indexOf(".") + 1);
			}

			if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			} else if (filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			}
		}

		TypedQuery<Departamento> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		query.setFirstResult(filtro.getPrimeiroRegistro());
		query.setMaxResults(filtro.getQuantidadeDeRegistros());

		return query.getResultList();
	}

	public int quantidadeDepartamentosFiltrados(DepartamentoFiltro filtro) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Departamento> departamentoRoot = criteriaQuery.from(Departamento.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, departamentoRoot);

		criteriaQuery.select(builder.count(departamentoRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}

	private List<Predicate> criarPredicatesParaFiltro(DepartamentoFiltro filtro, Root<Departamento> departamentoRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(departamentoRoot.<String>get("nome"), "%" + filtro.getNome() + "%"));
		}

		return predicates;
	}

}
