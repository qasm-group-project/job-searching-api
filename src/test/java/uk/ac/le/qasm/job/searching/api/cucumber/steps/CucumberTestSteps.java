package uk.ac.le.qasm.job.searching.api.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import uk.ac.le.qasm.job.searching.api.Application;
import uk.ac.le.qasm.job.searching.api.cucumber.utils.MessageFieldExtractor;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.repository.JobPostRepository;
import uk.ac.le.qasm.job.searching.api.repository.ProviderRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestSteps {

    @LocalServerPort
    private Long port;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;
    private RestClientResponseException ex;
    private String token;
    private String jobPostId;

    @Given("the tables are empty")
    public void the_tables_are_empty() {
        jobPostRepository.deleteAll();
        providerRepository.deleteAll();
    }

    @Given("the job provider is created with")
    public void theJobProviderIsCreatedWith(String docString) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/provider/register",
                                              HttpMethod.POST,
                                              new HttpEntity<>(docString, headers),
                                              String.class);
    }

    @Given("the job provider is logged in with username {string} and password {string}")
    public void theJobProviderIsLoggedInWithUsernameAndPassword(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/provider/login",
                                                                  HttpMethod.POST,
                                                                  new HttpEntity<>(Map.of("username", username, "password", password), headers),
                                                                  JsonNode.class);

        this.token = Objects.requireNonNull(response.getBody()).get("token").asText();
    }

    @Given("a post is created with")
    public void aPostIsCreatedWith(String docString) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/job-post/create",
                                                                  HttpMethod.POST,
                                                                  new HttpEntity<>(docString, headers),
                                                                  JsonNode.class);

        jobPostId = Objects.requireNonNull(response.getBody()).get("id").asText();
    }

    @When("I call the update job post path with the following body")
    public void iCallTheUpdateJobPostPathWithTheFollowingBody(String docString) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/job-post/" + jobPostId,
                                                  HttpMethod.PUT,
                                                  new HttpEntity<>(docString, headers),
                                                  String.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the create job seeker path with the following body")
    public void iCallTheCreateJobSeekerPathWithTheFollowingBody(String docString) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/seeker/register",
                                                  HttpMethod.POST,
                                                  new HttpEntity<>(docString, headers),
                                                  String.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @Then("the status returned must be {int}")
    public void the_status_returned_must_be(Integer httpStatus) {
        if (response != null) {
            assertEquals(httpStatus, response.getStatusCode().value());
        } else {
            assertEquals(httpStatus, ex.getStatusCode().value());
        }
    }

    @Then("the field {string} returned must be {string}")
    public void the_field_returned_must_be(String field, String value) throws JsonProcessingException {
        JsonNode jsonResponse;
        if (response != null) {
            jsonResponse = objectMapper.readTree(response.getBody());
        } else {
            jsonResponse = objectMapper.readTree(ex.getResponseBodyAsString());
        }

        if (value.equals("not null")) {
            assertNotNull(MessageFieldExtractor.getResponseFieldValue(jsonResponse, field));
        } else {
            assertEquals(value, MessageFieldExtractor.getResponseFieldValue(jsonResponse, field));
        }
    }

    @Then("the job post with the title {string} must have the status {string}")
    public void theJobPostWithTheTitleMustHaveTheStatus(String title, String status) {
        Optional<JobPost> jobPost = jobPostRepository.findByTitle(title);

        assertEquals(status, jobPost.orElseThrow().getStatus().name());
    }

}
