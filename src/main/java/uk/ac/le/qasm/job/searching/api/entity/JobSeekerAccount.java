package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "seeker", schema = "job_searching")
public class JobSeekerAccount {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "username")
    @JsonProperty(value = "username")
    private String username;

    @Column(name = "password")
    @JsonProperty(value = "password")
    private String password;

    @Column(name = "nickname")
    @JsonProperty(value = "nickname")
    private String nickname;

    @Column(name = "email")
    @JsonProperty(value = "email")
    private String email;

    @Column(name = "phone")
    @JsonProperty(value = "phone")
    private String phone;

    @Column(name = "firstname")
    @JsonProperty(value = "firstname")
    private String firstname;

    @Column(name = "lastname")
    @JsonProperty(value = "lastname")
    private String lastname;

    @Column(name = "gender")
    @JsonProperty(value = "gender")
    private String gender;
}
