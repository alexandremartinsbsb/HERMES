package hermes.util;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {

	private final Weld weld;
	private final WeldContainer container;

	public WeldJUnit4Runner(final Class<?> testClass) throws InitializationError {

		super(testClass);
		this.weld = new Weld();
		this.container = weld.initialize();
		RequestContext requestContext = container.instance().select(RequestContext.class, UnboundLiteral.INSTANCE).get();
		requestContext.activate();
	}

	@Override
	protected Object createTest() throws Exception {

		TestClass testClassJunitModel = getTestClass();
		final Class<?> testClass = testClassJunitModel.getJavaClass();
		Object testInstance = testClass.newInstance();
		Field[] fields = testClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			Inject injectAnnotation = field.getAnnotation(Inject.class);
			if (injectAnnotation != null) {
				Class<?> fieldType = field.getType();
				Object fieldValueInjectedFromCdi = container.instance().select(fieldType).get();
				field.set(testInstance, fieldValueInjectedFromCdi);
			}

		}
		return testInstance;
	}

}
