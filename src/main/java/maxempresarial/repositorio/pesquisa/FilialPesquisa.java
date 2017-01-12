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

import maxempresarial.modelo.Filial;
import maxempresarial.repositorio.filtro.FilialFiltro;

public class FilialPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager gerenciadorEntidade;

	@Inject
	public FilialPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public List<Filial> filtradas(FilialFiltro filtro) {
		From<?, ?> orderByFromEntity = null;

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Filial> criteriaQuery = builder.createQuery(Filial.class);

		Root<Filial> filialRoot = criteriaQuery.from(Filial.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, filialRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = filialRoot;

			if (filtro.getPropriedadeParaOrdenacao().contains(".")) {
				nomePropriedadeOrdenacao = nomePropriedadeOrdenacao.substring(filtro.getPropriedadeParaOrdenacao().indexOf(".") + 1);
			}

			if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			} else if (filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			}
		}

		TypedQuery<Filial> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		query.setFirstResult(filtro.getPrimeiroRegistro());
		query.setMaxResults(filtro.getQuantidadeDeRegistros());

		return query.getResultList();
	}

	public int quantidadeFiliaisFiltradas(FilialFiltro filtro) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Filial> filialRoot = criteriaQuery.from(Filial.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, filialRoot);

		criteriaQuery.select(builder.count(filialRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}

	private List<Predicate> criarPredicatesParaFiltro(FilialFiltro filtro, Root<Filial> filialRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNomeFantasia())) {
			predicates.add(builder.like(filialRoot.<String>get("nomeFantasia"), "%" + filtro.getNomeFantasia() + "%"));
		}

		return predicates;
	}
}
