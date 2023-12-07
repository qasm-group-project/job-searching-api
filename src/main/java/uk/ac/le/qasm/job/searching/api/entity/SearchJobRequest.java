package uk.ac.le.qasm.job.searching.api.entity;
import lombok.Data;

@Data
public class SearchJobRequest {
    private String job_type;
    private String title;
    private String category;
    private int size = 10;
    private int page = 0;
}

