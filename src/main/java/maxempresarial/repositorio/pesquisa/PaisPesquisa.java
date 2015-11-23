package maxempresarial.repositorio.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

	private Criteria criarCriteriaParaFiltro(PaisFiltro filtro) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoHibernate.createCriteria(Pais.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			resultado.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return resultado;
	}

	public int quantidadePaisesFiltrados(PaisFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setProjection(Projections.rowCount());

		return ((Number) resultado.setCacheable(true).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Pais> filtrados(PaisFiltro filtro) {
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
