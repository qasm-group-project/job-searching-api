package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seeker_feedbacks", schema = "job_searching")
public class SeekerFeedback {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "application_id")
    @JsonProperty(value = "application_id", access = JsonProperty.Access.READ_ONLY)
    private UUID applicationId;

    @Column(name = "feedback", columnDefinition = "TEXT")
    @JsonProperty("feedback")
    private String feedback;

}
