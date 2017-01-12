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

import maxempresarial.modelo.Usuario;
import maxempresarial.repositorio.filtro.UsuarioFiltro;

public class UsuarioPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager gerenciadorEntidade;

	@Inject
	public UsuarioPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public List<Usuario> filtrados(UsuarioFiltro filtro) {
		From<?, ?> orderByFromEntity = null;

		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (filtro.getPropriedadeParaOrdenacao() != null) {
			String nomePropriedadeOrdenacao = filtro.getPropriedadeParaOrdenacao();
			orderByFromEntity = usuarioRoot;

			if (filtro.getPropriedadeParaOrdenacao().contains(".")) {
				nomePropriedadeOrdenacao = nomePropriedadeOrdenacao.substring(filtro.getPropriedadeParaOrdenacao().indexOf(".") + 1);
			}

			if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			} else if (filtro.getPropriedadeParaOrdenacao() != null) {
				criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
			}
		}

		TypedQuery<Usuario> query = this.gerenciadorEntidade.createQuery(criteriaQuery);
		query.setFirstResult(filtro.getPrimeiroRegistro());
		query.setMaxResults(filtro.getQuantidadeDeRegistros());

		return query.getResultList();
	}

	public int quantidadeUsuariosFiltrados(UsuarioFiltro filtro) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

		List<Predicate> predicates = criarPredicatesParaFiltro(filtro, usuarioRoot);

		criteriaQuery.select(builder.count(usuarioRoot));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = this.gerenciadorEntidade.createQuery(criteriaQuery);

		return query.getSingleResult().intValue();
	}

	private List<Predicate> criarPredicatesParaFiltro(UsuarioFiltro filtro, Root<Usuario> usuarioRoot) {
		CriteriaBuilder builder = this.gerenciadorEntidade.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(usuarioRoot.<String>get("nome"), "%" + filtro.getNome() + "%"));
		}

		return predicates;
	}
}
