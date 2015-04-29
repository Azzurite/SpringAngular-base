package name.azzurite.baseproject.config;

import name.azzurite.baseproject.domain.entity.conversion.AutoConfiguredDozerConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.classmap.MappingFileData;
import org.dozer.loader.DozerBuilder;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan("name.azzurite.baseproject.domain.entity.conversion")
public class DozerConfig {
	/**
	 * Abuses the BeanMappingBuilder to build the {@link org.dozer.classmap.Configuration} object programatically
	 * instead of via XML
	 */
	@Bean
	public BeanMappingBuilder buildConfiguration(List<AutoConfiguredDozerConverter> customConverters) {
		// since Dozer doesn't explicitly support programatically setting the Configuration, we have to abuse the
		// BeanMappingBuilder class a bit. In the end, this doesn't build
		// bean mappings anymore and instead builds the configuration.
		return new BeanMappingBuilder() {
			// normally this would be hidden and managed by the BeanMappingBuilder class
			private DozerBuilder dozerBuilder = new DozerBuilder();

			@Override
			protected void configure() {
				DozerBuilder.ConfigurationBuilder configuration = dozerBuilder.configuration();
				customConverters.stream().forEach((converter) -> {
					configuration.customConverter(converter.getClass())
								 .classA(converter.getClassA())
								 .classB(converter.getClassB());
				});
			}

			@Override
			public MappingFileData build() {
				// this is exactly the same as the overridden implementation, except that it uses another DozerBuilder
				configure();
				return dozerBuilder.build();
			}
		};
	}

	@Bean
	public DozerBeanMapper mapper(BeanMappingBuilder mappingBuilder) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(mappingBuilder);
		return mapper;
	}
}
