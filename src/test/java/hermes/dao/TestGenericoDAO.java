package hermes.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jintegrity.core.JIntegrity;

import hermes.util.WeldJUnit4Runner;
import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.repositorio.dao.entidade.EmpresaDAO;

@RunWith(WeldJUnit4Runner.class)
public class TestGenericoDAO {

	private JIntegrity helper = new JIntegrity();

	@Inject
	private EmpresaDAO empresaDAO;

	@Before
	public void init() {
		this.helper.cleanAndInsert();
	}

	@Test
	public void testGuardar() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemover() {
		fail("Not yet implemented");
	}

	@Test
	public void testPorPk() throws ExcecaoDAO {
		Empresa empresaUm = this.empresaDAO.porPk(1L);
		Empresa empresaDois = this.empresaDAO.porPk(2L);

		assertEquals(1L, empresaUm.getPk().longValue());
		assertEquals(2L, empresaDois.getPk().longValue());
	}

}
