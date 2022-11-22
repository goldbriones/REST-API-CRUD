package RESTAPICRUD.ControllerTest;

import com.accenture.RESTAPICRUD.Controller.EmployeeController;
import com.accenture.RESTAPICRUD.Entity.Employee;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EmployeeController.class)
@WithMockUser
public class ControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    Employee mockEmployee = new Employee
            (1, "Gold", "Briones", "gbriones@gmail.com", "Java Dev");

    String exampleJsonEmployee =
            "{\"id\":\"1\",\"firstName\":\"Gold\"," +
                    "\"lastName\":\"Briones\"," +
                    "emailId\":\"gbriones@gmail.com\"," +
                    "\"roleName\":\"Java Dev\"}";

    @Test
    public void getEmployeeByIdTest() throws Exception {

//        Mockito.when(employeeService.findEmployeeById
//                        (Mockito.anyString(), Mockito.anyString()))
//                .thenReturn(mockEmployee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get
                ("/employee/{id}").accept(MediaType.APPLICATION_JSON);

//        log.info();
//        String expected = "{\"id\":\"1\",\"firstName\":\"Gold\"," +
//                "\"lastName\":\"Briones\"," +
//                "emailId\":\"gbriones@gmail.com\"," +
//                "\"roleName\":\"Java Dev\"}";
//
//        JSONAssert.assertEquals(expected, result.getResponse()
//                .getContentAsString(), false);
    }
}
