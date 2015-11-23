package maxempresarial.repositorio.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

	public Filial porCnpj(String cnpj) {
		Session sessaoDoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoDoHibernate.createCriteria(Filial.class);
		try {
			if (cnpj != null && cnpj.trim().length() > 0) {
				return (Filial) resultado.add(Restrictions.eq("cnpj", cnpj)).uniqueResult();
			} else {
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		}
	}

	private Criteria criarCriteriaParaFiltro(FilialFiltro filtro) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoHibernate.createCriteria(Filial.class);

		if (filtro.getNomeFantasia() != null && filtro.getNomeFantasia().trim().length() > 0) {
			resultado.add(Restrictions.ilike("nomeFantasia", filtro.getNomeFantasia(), MatchMode.ANYWHERE));
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Filial> filtradas(FilialFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setFirstResult(filtro.getPrimeiroRegistro());
		resultado.setMaxResults(filtro.getQuantidadeDeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.asc(filtro.getPropriedadeParaOrdenacao()));
		} else if (filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.desc(filtro.getPropriedadeParaOrdenacao()));
		}
		return resultado.setCacheRegion("cacheFilial").list();
	}

	public int quantidadeFiliaisFiltradas(FilialFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);
		resultado.setProjection(Projections.rowCount());
		return ((Number) resultado.setCacheable(true).uniqueResult()).intValue();
	}
}
