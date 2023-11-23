package uk.ac.le.qasm.job.searching.api.entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class JobSeekerRequest {

    @NotEmpty(message = "The nickname is required.")
    private String nickname;

    @NotEmpty(message = "The email is required.")
    private String email;

    @NotEmpty(message = "The phone is required.")
    private String phone;

    @NotEmpty(message = "The firstname is required.")
    private String firstname;

    @NotEmpty(message = "The lastname is required.")
    private String lastname;

    @NotEmpty(message = "The gender is required.")
    private String gender;

}
