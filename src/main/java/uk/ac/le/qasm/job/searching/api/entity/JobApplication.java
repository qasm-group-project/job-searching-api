package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_application", schema = "job_searching")
public class JobApplication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @OneToOne
    @JsonProperty("applicant")
    private JobSeeker applicant;

    @ManyToOne
    @JsonProperty("job_post")
    private JobPost jobPost;

}
