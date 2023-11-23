package uk.ac.le.qasm.job.searching.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeekerSocialMediaRequestUpdate {
    @NotEmpty(message = "The full link is required.")
    private String link;
}