package name.azzurite.baseproject.domain.entity.conversion;

import name.azzurite.baseproject.domain.entity.component.UniqueName;
import org.springframework.stereotype.Component;

@Component
public class StringUniqueNameConverter extends AutoConfiguredDozerConverter<String, UniqueName> {
	public StringUniqueNameConverter() {
		super(String.class, UniqueName.class);
	}

	@Override
	public UniqueName convertTo(String source, UniqueName destination) {
		return new UniqueName(source);
	}

	@Override
	public String convertFrom(UniqueName source, String destination) {
		return source.getUniqueName();
	}
}
