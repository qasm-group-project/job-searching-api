package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_application", schema = "job_searching")
public class JobSeekerApplication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @ManyToOne
    @JsonProperty("applicant")
    private JobSeeker applicant;

    @ManyToOne
    @JsonProperty("job_post")
    private JobPost jobPost;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    @JsonProperty("provider_feedbacks")
    private Set<ProviderFeedback> providerProviderFeedbacks;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    @JsonProperty("seeker_feedbacks")
    private Set<ProviderFeedback> seekerProviderFeedbacks;

    @JsonGetter("provider_feedbacks")
    public Set<String> transformProviderFeedbacks() {
        return Optional.ofNullable(providerProviderFeedbacks)
                       .orElse(new HashSet<>())
                       .stream()
                       .map(ProviderFeedback::getFeedback)
                       .collect(Collectors.toSet());
    }

    @JsonGetter("seeker_feedbacks")
    public Set<String> transformSeekerFeedbacks() {
        return Optional.ofNullable(seekerProviderFeedbacks)
                       .orElse(new HashSet<>())
                       .stream()
                       .map(ProviderFeedback::getFeedback)
                       .collect(Collectors.toSet());
    }

}
