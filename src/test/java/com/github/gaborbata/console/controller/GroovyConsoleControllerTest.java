package com.github.gaborbata.console.controller;

import com.github.gaborbata.console.configuration.JGivenConfig;
import com.github.gaborbata.console.controller.stage.GroovyConsoleControllerStage;
import com.tngtech.jgiven.integration.spring.junit5.SimpleSpringScenarioTest;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@ExtendWith(JGivenExtension.class)
@SpringBootTest(classes = {JGivenConfig.class}, webEnvironment = WebEnvironment.NONE)
public class GroovyConsoleControllerTest extends SimpleSpringScenarioTest<GroovyConsoleControllerStage> {

    private static final String GROOVY_CONSOLE_ENDPOINT = "/console/groovy";

    @Test
    public void should_include_script_return_result_in_the_result_object() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script("2 + 3");
        when().post_request_is_received();
        then().the_status_is(HttpStatus.OK)
                .and().the_content_is("{'result': 5}");
    }

    @Test
    public void should_include_script_output_in_the_result_object() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script("println 2 + 3");
        when().post_request_is_received();
        then().the_status_is(HttpStatus.OK)
                .and().the_content_is("{'output': ['5']}");
    }

    @Test
    public void should_include_both_output_and_result_object_in_the_result() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script("println('test'); 2 + 3");
        when().post_request_is_received();
        then().the_status_is(HttpStatus.OK)
                .and().the_content_is("{'output': ['test'], 'result': 5}");
    }

    @Test
    public void should_respond_with_bad_request_when_script_is_missing() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script(null);
        when().post_request_is_received();
        then().the_status_is(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void should_include_spring_application_context_in_bound_variables() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script("applicationContext != null");
        when().post_request_is_received();
        then().the_status_is(HttpStatus.OK)
                .and().the_content_is("{'result': true}");
    }

    @Test
    public void should_include_exception_message_in_the_result() throws Exception {
        given().an_endpoint(GROOVY_CONSOLE_ENDPOINT)
                .and().a_groovy_script_$script("throw new RuntimeException('test')");
        when().post_request_is_received();
        then().the_status_is(HttpStatus.OK)
                .and().the_content_is("{'output': ['java.lang.RuntimeException: test']}");
    }
}
