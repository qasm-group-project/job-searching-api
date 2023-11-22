package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.enums.ApplicationStatus;

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

    @OneToOne
    @JsonProperty("job_post")
    private JobPost jobPost;

    @Column(name = "application_status")
    @JsonProperty("application_status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

}
