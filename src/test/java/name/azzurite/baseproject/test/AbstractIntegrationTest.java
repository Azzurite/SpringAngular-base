package name.azzurite.baseproject.test;

import name.azzurite.baseproject.config.constants.BuildConstants;
import name.azzurite.baseproject.config.constants.WebConstants;
import name.azzurite.baseproject.test.config.IntegrationTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IntegrationTestConfig.class})
@EnableAutoConfiguration
@WebIntegrationTest
@ActiveProfiles(BuildConstants.SPRING_PROFILE_INTEGRATION)
@Transactional
public abstract class AbstractIntegrationTest {

	@Inject
	private WebApplicationContext wac;

	private final List<FieldRestoreInfo> fieldsToRestore = new ArrayList<>();

	/**
	 * MockMvc object with the default accepting type of application/json
	 */
	protected MockMvc mockMvc;

	/**
	 * Initializes Mockito mocks and sets up the mockMvc.
	 */
	@Before
	public final void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
								 .defaultRequest(MockMvcRequestBuilders
										 .get("/")
										 .accept(WebConstants.MEDIA_TYPE_JSON))
								 .build();
	}


	/**
	 * Finds the bean of type S and replaces the field of type T with the given new field value. Restores the old value
	 * after the Test ran.
	 *
	 * @param beanClass the bean class where the field should be replaced
	 * @param newFieldValue the new value for the field
	 * @param <S> the class of the bean where the field should be injected
	 * @param <T> the class of the field
	 */
	protected <S, T> void replaceBeanFieldForTest(Class<S> beanClass, T newFieldValue) {
		try {
			List<Field> fieldsToMock = Arrays.stream(beanClass.getDeclaredFields())
											 .filter((field) -> field.getType()
																	 .isAssignableFrom(newFieldValue.getClass()))
											 .collect(Collectors.toList());

			S bean = wac.getBean(beanClass);
			for (Field field : fieldsToMock) {
				ReflectionUtils.makeAccessible(field);
				saveToRestoreAfterTest(bean, field, field.get(bean));
				field.set(bean, newFieldValue);
			}
		} catch (IllegalAccessException e) {
			// should never happen because we used ReflectionUtils.makeAccessible(Field);
			throw new RuntimeException(e);
		}
	}

	private class FieldRestoreInfo {
		private final Object objectToRestore;

		private final Field fieldToRestore;

		private final Object restoreValue;

		public FieldRestoreInfo(Object objectToRestore, Field fieldToRestore, Object restoreValue) {
			this.objectToRestore = objectToRestore;
			this.fieldToRestore = fieldToRestore;
			this.restoreValue = restoreValue;
		}

		public Object getObjectToRestore() {
			return objectToRestore;
		}

		public Field getFieldToRestore() {
			return fieldToRestore;
		}

		public Object getRestoreValue() {
			return restoreValue;
		}

	}


	private void saveToRestoreAfterTest(Object object, Field field, Object value) {
		fieldsToRestore.add(new FieldRestoreInfo(object, field, value));
	}

	private void restore(FieldRestoreInfo info) {
		try {
			ReflectionUtils.makeAccessible(info.getFieldToRestore());
			info.getFieldToRestore().set(info.getObjectToRestore(), info.getRestoreValue());
		} catch (IllegalAccessException e) {
			// should never happen because we used ReflectionUtils.makeAccessible(Field);
			throw new RuntimeException(e);
		}
	}

	@After
	public final void restoreOriginalFields() {
		fieldsToRestore.stream().forEach(this::restore);
	}

}
