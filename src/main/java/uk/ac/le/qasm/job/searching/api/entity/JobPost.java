package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.enums.JobType;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "provider")
@Table(name = "job_posts", schema = "job_searching")
public class JobPost {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Setter
    @Column(name = "title")
    @JsonProperty(value = "title")
    private String title;

    @Setter
    @Column(name = "description", columnDefinition="TEXT")
    @JsonProperty(value = "description")
    private String description;

    @Setter
    @Column(name = "salary")
    @JsonProperty(value = "salary")
    private String salary;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    @JsonProperty("job_type")
    private JobType jobType;

    @Setter
    @Column(name = "is_visible")
    @JsonProperty(value = "is_visible")
    private Boolean isVisible;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty(value = "status")
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "provider_uuid")
    @JsonIgnore
    private Provider provider;

}
