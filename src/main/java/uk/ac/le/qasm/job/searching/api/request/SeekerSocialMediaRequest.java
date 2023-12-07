package uk.ac.le.qasm.job.searching.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeekerSocialMediaRequest {
    @NotEmpty(message = "The full platform is required.")
    private String platform;
    @NotEmpty(message = "The full link is required.")
    private String link;
}
