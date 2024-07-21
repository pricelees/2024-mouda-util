package mouda.util.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "team")
public record TeamProperties(
	String name,
	Parts parts
) {

	public record Parts(Backend backend, Frontend frontend) {
	}

	public record Backend(List<String> members) {
	}

	public record Frontend(List<String> members) {
	}
}
