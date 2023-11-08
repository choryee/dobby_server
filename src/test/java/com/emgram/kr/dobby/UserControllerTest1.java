import com.emgram.kr.dobby.controller.UserController;
import com.emgram.kr.dobby.dto.login.UserLoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(classes = UserControllerTest1.class)
@AutoConfigureMockMvc
public class UserControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testLogin() throws Exception {
        // Create a UserLoginRequest object with the required data
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUserName("testuser");
        loginRequest.setPassword("testpassword");

        // Convert the object to JSON
        String jsonRequest = "{ \"userName\": \"testuser\", \"password\": \"testpassword\" }";

        // Perform the POST request to /api/vi/users/login
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/vi/users/login")
                .header("Accept", "*/*")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        // Verify that the response status is OK (200)
        result.andExpect(MockMvcResultMatchers.status().isOk());

        // You can add more assertions to check the response content if needed
    }
}
