package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class JobPostApplication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @ManyToOne
    @JsonProperty("applicant")
    private JobSeeker applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private JobPost jobPost;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    @JsonProperty("provider_feedbacks")
    private Set<ProviderFeedback> providerFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    @JsonProperty("seeker_feedbacks")
    private Set<SeekerFeedback> seekerFeedbacks;

    @JsonGetter("provider_feedbacks")
    public Set<String> transformProviderFeedbacks() {
        return Optional.ofNullable(providerFeedbacks)
                       .orElse(new HashSet<>())
                       .stream()
                       .map(ProviderFeedback::getFeedback)
                       .collect(Collectors.toSet());
    }

    @JsonGetter("seeker_feedbacks")
    public Set<String> transformSeekerFeedbacks() {
        return Optional.ofNullable(seekerFeedbacks)
                       .orElse(new HashSet<>())
                       .stream()
                       .map(SeekerFeedback::getFeedback)
                       .collect(Collectors.toSet());
    }

    public void addFeedback(ProviderFeedback providerFeedback) {
        Optional.ofNullable(providerFeedbacks).orElse(new HashSet<>()).add(providerFeedback);
    }

    public void addSeekerFeedback(SeekerFeedback seekerFeedback) {
        Optional.ofNullable(seekerFeedbacks).orElse(new HashSet<>()).add(seekerFeedback);
    }

}
