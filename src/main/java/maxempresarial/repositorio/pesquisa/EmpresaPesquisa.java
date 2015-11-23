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

import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.filtro.EmpresaFiltro;

/**
 * Classe para realizar qualquer tipo de pesquisa para o objeto EMPRESA;
 * 
 * @author Alexandre Martins da Silva
 * 
 * @version 1.00
 * 
 */

public class EmpresaPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	private final EntityManager gerenciadorEntidade;

	@Inject
	public EmpresaPesquisa(EntityManager gerenciadorEntidade) {
		super();
		this.gerenciadorEntidade = gerenciadorEntidade;
	}

	public Empresa porCnpj(String cnpj) {
		Session sessaoDoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoDoHibernate.createCriteria(Empresa.class);
		try {
			if (StringUtils.isNotBlank(cnpj)) {
				return (Empresa) resultado.add(Restrictions.eq("cnpj", cnpj)).uniqueResult();
			} else {
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		}
	}

	private Criteria criarCriteriaParaFiltro(EmpresaFiltro filtro) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria resultado = sessaoHibernate.createCriteria(Empresa.class);

		if (StringUtils.isNotBlank(filtro.getNomeFantasia())) {
			resultado.add(Restrictions.ilike("nomeFantasia", filtro.getNomeFantasia(), MatchMode.ANYWHERE));
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> filtradas(EmpresaFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setFirstResult(filtro.getPrimeiroRegistro());
		resultado.setMaxResults(filtro.getQuantidadeDeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.asc(filtro.getPropriedadeParaOrdenacao()));
		} else if (filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.desc(filtro.getPropriedadeParaOrdenacao()));
		}

		return resultado.setCacheRegion("cacheEmpresa").list();
	}

	public int quantidadeEmpresasFiltradas(EmpresaFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setProjection(Projections.rowCount());

		return ((Number) resultado.setCacheable(true).uniqueResult()).intValue();
	}
}
