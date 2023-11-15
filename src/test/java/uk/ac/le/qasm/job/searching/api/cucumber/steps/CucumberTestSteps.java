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
import uk.ac.le.qasm.job.searching.api.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestSteps {

    @LocalServerPort
    private Long port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;
    private RestClientResponseException ex;

    @Given("the tables are empty")
    public void the_tables_are_empty() {
        userRepository.deleteAll();
    }

    @When("I call the user creation path with the following body")
    public void i_call_the_user_creation_path_with_the_following_body(String docString) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        try {
            this.response = restTemplate.exchange("http://localhost:" + port + "/user",
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
            assertNotNull(jsonResponse.get(field).textValue());
        } else {
            assertEquals(jsonResponse.get(field).textValue(), value);
        }
    }

}
