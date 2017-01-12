package maxempresarial.repositorio.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import maxempresarial.modelo.Cidade;
import maxempresarial.modelo.Estado;
import maxempresarial.repositorio.filtro.CidadeFiltro;

public class CidadePesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager gerenciadorEntidade;

	@Inject
	public CidadePesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public int quantidadeCidadesFiltrados(CidadeFiltro filtro) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Cidade> cidadeRoot = criteriaQuery.from(Cidade.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, cidadeRoot);

		criteriaQuery.select(builder.count(cidadeRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}

	public List<Cidade> filtrados(CidadeFiltro filtro) {
		From<?, ?> orderByFromEntity = null;
		List<Cidade> listaCidades = new ArrayList<>();

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

		Root<Cidade> cidadeRoot = criteriaQuery.from(Cidade.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, cidadeRoot);
		criteriaQuery.multiselect(
				cidadeRoot.get("pk"), 
				cidadeRoot.get("versao"), 
				cidadeRoot.get("nome"),
				cidadeRoot.get("ddd"),
				cidadeRoot.get("estado"));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = cidadeRoot;

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
			Cidade cidade = new Cidade();

			cidade.setPk((Long) obejeto[0]);
			cidade.setVersao((Integer) obejeto[1]);
			cidade.setNome((String) obejeto[2]);
			cidade.setDdd((String) obejeto[3]);
			cidade.setEstado((Estado) obejeto[4]);

			listaCidades.add(cidade);
		}

		return listaCidades;
	}

	private List<Predicate> criarPredicatesParaFiltro(CidadeFiltro filtro, Root<Cidade> cidadeRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(cidadeRoot.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		if (filtro.getEstado() != null) {
			predicates.add(cidadeRoot.get("estado").in(Arrays.asList(filtro.getEstado().getPk())));
		}

		return predicates;
	}

}
