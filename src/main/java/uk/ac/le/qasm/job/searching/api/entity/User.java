package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "job_searching")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "name")
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "type")
    @JsonProperty(value = "type")
    private String type;


}
