package edu.spring.data

import com.fasterxml.jackson.databind.ObjectMapper
import edu.spring.data.model.Address
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class ApiTest extends Specification {

    @Autowired
    private MockMvc client;

    @Autowired
    private ObjectMapper jackson

    @Unroll
    def "Should correctly apply pagination"() {
        when: "Call to api to retrieve addresses"
        def callResult = client.perform(
                MockMvcRequestBuilders.get("/api/address")
                        .param("pageSize", pageSize)
                        .param("pageNumber", pageNumber))
                .andExpect(status().isOk())

        and: "Addresses extracted"
        def body = callResult.andReturn().getResponse().getContentAsString()
        def addresses = jackson.readValue(body, Address[].class)

        then: "Returned correct number of addresses"
        addresses.length == amount

        where:
        pageSize | pageNumber | amount
        "3"      | "1"        | 3
        "5"      | "2"        | 4
        "9"      | "2"        | 0
        "9"      | "1"        | 9
        "1"      | "10"       | 0
    }

    @Unroll
    def "Should correctly handle errors"() {
        expect:
        def callResult = client.perform(MockMvcRequestBuilders.get("/api/address")
                .param("pageSize", pageSize)
                .param("pageNumber", pageNumber))
                .andExpect(status().is(status))
        and:
        callResult.andReturn().getResponse().getErrorMessage() == message

        where:
        pageSize | pageNumber | status | message
        "f"      | "1"        | 400    | "Non integer parameter supplied."
        "2"      | "f"        | 400    | "Non integer parameter supplied."
        "0"      | "1"        | 400    | "Page size must not be less than one!"
    }
}
