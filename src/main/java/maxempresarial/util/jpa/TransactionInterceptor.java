package maxempresarial.util.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	@Inject
	public TransactionInterceptor(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception {
		EntityTransaction transacao = this.manager.getTransaction();
		boolean transacaoCriada = false;

		try {
			if (!transacao.isActive()) {
				transacao.begin();
				transacao.rollback();

				transacao.begin();
				transacaoCriada = true;
			}

			return context.proceed();
		} catch (Exception ex) {
			if (transacao != null && transacaoCriada) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			if (transacao != null && transacao.isActive() && transacaoCriada) {
				transacao.commit();
			}
		}
	}

}
