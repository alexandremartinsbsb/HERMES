package hermes.servico;

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import hermes.util.WeldJUnit4Runner;
import maxempresarial.modelo.Empresa;
import maxempresarial.repositorio.dao.ExcecaoDAO;
import maxempresarial.servico.EmpresaServico;
import maxempresarial.servico.ExcecaoRN;

@RunWith(WeldJUnit4Runner.class)
public class TestEmpresaServico {

	@Inject
	private Empresa empresa;

	@Inject
	private EmpresaServico empresaServico;

	@Before
	public void init() {
		this.empresa.setRazaoSocial("EMPRESA DE TESTE UNITARIO");
		this.empresa.setNomeFantasia("EMPRESA DE TESTE UNITARIO");
		this.empresa.setCnpj("76.371.792/0001-72");
		this.empresa.setResponsavel("RESPONSAVEL TESTE");
	}

	@Test
	public void testSalvar() throws ExcecaoRN, ExcecaoDAO {
		this.empresa = this.empresaServico.salvar(this.empresa);
	}

	@Test
	public void testExcluir() {
		fail("Not yet implemented");
	}

}
