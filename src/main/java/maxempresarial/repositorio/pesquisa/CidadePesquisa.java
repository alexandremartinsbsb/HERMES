package maxempresarial.repositorio.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

	@SuppressWarnings("unchecked")
	public List<Cidade> porEstado(Estado estado) {
		Session sessaoDoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoDoHibernate.createCriteria(Cidade.class);
		try {
			if (estado != null) {
				return resultado.add(Restrictions.eq("estado", estado)).list();
			} else {
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		}
	}

	private Criteria criarCriteriaParaFiltro(CidadeFiltro filtro) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoHibernate.createCriteria(Cidade.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			resultado.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return resultado;
	}

	public int quantidadeCidadesFiltrados(CidadeFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setProjection(Projections.rowCount());

		return ((Number) resultado.setCacheable(true).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> filtrados(CidadeFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setFirstResult(filtro.getPrimeiroRegistro());
		resultado.setMaxResults(filtro.getQuantidadeDeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.asc(filtro.getPropriedadeParaOrdenacao()));
		} else if (filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.desc(filtro.getPropriedadeParaOrdenacao()));
		}
		return resultado.setCacheable(true).list();
	}

}
