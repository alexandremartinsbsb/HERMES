package maxempresarial.util.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class ProdutorDeEntityManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManagerFactory fabricaEntityManager;

	public ProdutorDeEntityManager() {
		this.fabricaEntityManager = Persistence.createEntityManagerFactory("HermesPU");//TesteHermesPU
	}

	@Produces
	@RequestScoped
	public EntityManager constroiEntityManager() {
		return this.fabricaEntityManager.createEntityManager();
	}

	public void fechaEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}

}
