package uk.ac.le.qasm.job.searching.api.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Before;
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
import uk.ac.le.qasm.job.searching.api.repository.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestSteps {

    @LocalServerPort
    private Long port;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private ProviderSocialMediaRepository providerSocialMediaRepository;

    @Autowired
    private SeekerSocialMediaRepository seekerSocialMediaRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<JsonNode> response;
    private RestClientResponseException ex;
    private String token;
    private String jobPostId;
    private String socialMediaId;

    @Before
    public void init() {
        this.response = null;
        this.ex = null;
        this.token = null;
        this.jobPostId = null;
        this.socialMediaId = null;
    }

    @Given("the tables are empty")
    public void the_tables_are_empty() {
        providerSocialMediaRepository.deleteAll();
        seekerSocialMediaRepository.deleteAll();
        jobApplicationRepository.deleteAll();
        jobPostRepository.deleteAll();
        providerRepository.deleteAll();
        jobSeekerRepository.deleteAll();

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
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/job-post/create",
                                             HttpMethod.POST,
                                             new HttpEntity<>(docString, headers),
                                             JsonNode.class);

            jobPostId = Objects.requireNonNull(response.getBody()).get("id").asText();
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @Given("the header is empty")
    public void theHeaderIsEmpty() {
        this.token = null;
    }

    @When("I call the update job post path with the following body")
    public void iCallTheUpdateJobPostPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/job-post/" + jobPostId,
                                                  HttpMethod.PUT,
                                                  new HttpEntity<>(docString, headers),
                                                  JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the create job seeker path with the following body")
    @Given("the job seeker is created with")
    public void iCallTheCreateJobSeekerPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/seeker/register",
                                                  HttpMethod.POST,
                                                  new HttpEntity<>(docString, headers),
                                                  JsonNode.class);

            this.token = Objects.requireNonNull(response.getBody()).get("token").asText();
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @Given("the job seeker is logged in with username {string} and password {string}")
    @When("I call the login path with username {string} and password {string}")
    public void iCallTheLoginPathWithUsernameAndPassword(String username, String password) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/seeker/login",
                                                  HttpMethod.POST,
                                                  new HttpEntity<>(Map.of("username", username, "password", password), headers),
                                                  JsonNode.class);

            this.token = null;
            this.token = Objects.requireNonNull(response.getBody()).get("token").asText();
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the search jobs path")
    public void iCallTheSearchJobsPath() {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/job-posts",
                                                  HttpMethod.GET,
                                                  new HttpEntity<>(headers),
                                                  JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the apply for jobs path for the job {string}")
    public void iCallTheApplyForJobsPathForTheJob(String title) {
        JobPost jobPost = jobPostRepository.findByTitle(title)
                                           .orElseThrow(() -> new RuntimeException("Job does not exist"));

        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/job-posts/" + jobPost.getId() + "/apply",
                                                  HttpMethod.POST,
                                                  new HttpEntity<>(headers),
                                                  JsonNode.class);
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @Given("a social media is created with")
    public void aSocialMediaCreatedWith(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/social-media",
                    HttpMethod.POST,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

            socialMediaId = Objects.requireNonNull(response.getBody()).get("id").asText();
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @Given("a seeker social media is created with")
    public void aSeekerSocialMediaCreatedWith(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/social-media",
                    HttpMethod.POST,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

            socialMediaId = Objects.requireNonNull(response.getBody()).get("id").asText();
        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }
    @When("I call the update provider social media path with the following body")
    public void iCallTheUpdateProviderSocialMediaPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/social-media/" + socialMediaId,
                    HttpMethod.PUT,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the update seeker social media path with the following body")
    public void iCallTheUpdateSeekerSocialMediaPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/social-media/" + socialMediaId,
                    HttpMethod.PUT,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }
    @When("I call the update provider social media path with the following body with fake socialMediaId")
    public void iCallTheUpdateProviderSocialMediaPathWithTheFollowingBodyAndFakeSocialMediaId(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/social-media/" + UUID.randomUUID(),
                    HttpMethod.PUT,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the update seeker social media path with the following body with fake socialMediaId")
    public void iCallTheUpdateSeekerSocialMediaPathWithTheFollowingBodyAndFakeSocialMediaId(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/social-media/" + UUID.randomUUID(),
                    HttpMethod.PUT,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the delete provider social media path with the following body")
    public void iCallTheDeleteProviderSocialMediaPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/social-media/" + socialMediaId,
                    HttpMethod.DELETE,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the delete seeker social media path with the following body")
    public void iCallTheDeleteSeekerSocialMediaPathWithTheFollowingBody(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/social-media/" + socialMediaId,
                    HttpMethod.DELETE,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the delete provider social media path with the following body and fake socialMediaId")
    public void iCallTheDeleteProviderSocialMediaPathWithTheFollowingBodyAndFakeSocialMediaId(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/social-media/" + UUID.randomUUID(),
                    HttpMethod.DELETE,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call get all provider job posts")
    public void iCallGetAllProviderJobPosts(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/provider/job-post",
                    HttpMethod.GET,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

        } catch (RestClientResponseException ex) {
            this.ex = ex;
        }
    }

    @When("I call the delete seeker social media path with the following body and fake socialMediaId")
    public void iCallTheDeleteSeekerSocialMediaPathWithTheFollowingBodyAndFakeSocialMediaId(String docString) {
        this.response = null;
        this.ex = null;

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/api/v1/seeker/social-media/" + UUID.randomUUID(),
                    HttpMethod.DELETE,
                    new HttpEntity<>(docString, headers),
                    JsonNode.class);

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
            jsonResponse = response.getBody();
        } else {
            jsonResponse = objectMapper.readTree(ex.getResponseBodyAsString());
        }

        if (value.equals("not null")) {
            assertNotNull(MessageFieldExtractor.getResponseFieldValue(jsonResponse, field));
        } else if (value.equals("null")) {
            assertNull(MessageFieldExtractor.getResponseFieldValue(jsonResponse, field));
        } else {
            assertEquals(value, MessageFieldExtractor.getResponseFieldValue(jsonResponse, field));
        }
    }

    @Then("the array {string} returned must have size {string}")
    public void theArrayReturnedMustHaveSize(String field, String size) throws JsonProcessingException {
        JsonNode jsonResponse;
        if (response != null) {
            jsonResponse = response.getBody();
        } else {
            jsonResponse = objectMapper.readTree(ex.getResponseBodyAsString());
        }

        List<Object> array = MessageFieldExtractor.getResponseFieldArray(jsonResponse, field);

        assertNotNull(array);
        assertEquals(Integer.parseInt(size), array.size());
    }

    @Then("the job post with the title {string} must have the status {string}")
    public void theJobPostWithTheTitleMustHaveTheStatus(String title, String status) {
        Optional<JobPost> jobPost = jobPostRepository.findByTitle(title);

        assertEquals(status, jobPost.orElseThrow().getStatus().name());
    }

}
