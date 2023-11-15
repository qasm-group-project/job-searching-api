package uk.ac.le.qasm.job.searching.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.le.qasm.job.searching.api.entity.User;
import uk.ac.le.qasm.job.searching.api.usecase.CreateUserUseCase;
import uk.ac.le.qasm.job.searching.api.usecase.GetUserUseCase;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    //Injects the mocks into this class (this is the class we want to unit test)
    @InjectMocks
    private UserController userController;

    //Create the mocks from this class (we dont want to test this one, just mock its behaviour)
    @Mock
    private CreateUserUseCase createUserUseCase;

    //Create the mocks from this class (we dont want to test this one, just mock its behaviour)
    @Mock
    private GetUserUseCase getUserUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createSuccessTest() throws JsonProcessingException {
        User user = objectMapper.readValue("{\"name\": \"Test\", \"type\": \"ADMIN\"}", User.class);

        User createdUserFake = objectMapper.readValue("{\"id\": \"ea5de1d3-ae0b-409b-9a89-3e35a1800426\", \"name\": \"Test\", \"type\": \"ADMIN\"}", User.class);

        Mockito.when(createUserUseCase.create(Mockito.any())).thenReturn(createdUserFake);

        User userCreated = userController.create(user).getBody();

        Assertions.assertEquals(userCreated, createdUserFake);
    }

    @Test
    public void createFailureTest() {
        User user = new User();

        Mockito.when(createUserUseCase.create(Mockito.any())).thenThrow(new RuntimeException("error"));

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> userController.create(user));

        Assertions.assertEquals(ex.getMessage(), "error");
    }

}
