package uk.ac.le.qasm.job.searching.api.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProviderNewsRequest {
    @NotEmpty(message = "The full title is required.")
    private String title;
    @NotBlank(message = "The full description is required.")
    private String description;
}

