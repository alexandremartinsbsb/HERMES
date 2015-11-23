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

	public Usuario porEmail(String email) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria criteria = sessaoHibernate.createCriteria(Usuario.class);
		try {
			if (StringUtils.isNotBlank(email)) {
				return (Usuario) criteria.add(Restrictions.eq("email", email));
			} else {
				return null;
			}
		} catch (NoResultException nre) {
			return null;
		}

	}

	public Usuario porLogin(String login) {
		Usuario usuario = null;
		try {
			usuario = this.gerenciadorEntidade.createQuery("from Usuario where lower(login) = :login", Usuario.class).setParameter("login", login.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		return usuario;
	}

	private Criteria criarCriteriaParaFiltro(UsuarioFiltro filtro) {
		Session sessaoHibernate = this.gerenciadorEntidade.unwrap(Session.class);
		Criteria criteria = sessaoHibernate.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getEmail())) {
			criteria.add(Restrictions.eq("email", filtro.getEmail()));
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setFirstResult(filtro.getPrimeiroRegistro());
		resultado.setMaxResults(filtro.getQuantidadeDeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.asc(filtro.getPropriedadeParaOrdenacao()));
		} else if (filtro.getPropriedadeParaOrdenacao() != null) {
			resultado.addOrder(Order.desc(filtro.getPropriedadeParaOrdenacao()));
		}

		return resultado.list();

	}

	public int quantidadeUsuariosFiltrados(UsuarioFiltro filtro) {
		Criteria resultado = this.criarCriteriaParaFiltro(filtro);

		resultado.setProjection(Projections.rowCount());

		return ((Number) resultado.uniqueResult()).intValue();
	}
}
