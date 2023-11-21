package uk.ac.le.qasm.job.searching.api.result;

import lombok.Data;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
@Data
public class SearchAllJob {

    private String id;

    private String title;

    private String description;

    private String salary;

    private JobType jobType;

    private String company_name;

    private String company_contact_number;

    private String company_location;

}
