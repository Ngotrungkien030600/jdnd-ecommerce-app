import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	@JsonProperty
	private String confirmPassword;

	// Getters and setters
}
