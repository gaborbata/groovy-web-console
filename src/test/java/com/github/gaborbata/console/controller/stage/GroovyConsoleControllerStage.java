package com.github.gaborbata.console.controller.stage;

import com.github.gaborbata.console.controller.GroovyConsoleController;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@JGivenStage
public class GroovyConsoleControllerStage extends Stage<GroovyConsoleControllerStage> {

    @Autowired
    private GroovyConsoleController groovyConsoleController;

    private MockMvc mockMvc;

    private String uri;
    private String script;
    private ResultActions resultActions;

    @BeforeStage
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(groovyConsoleController).build();
    }

    public GroovyConsoleControllerStage an_endpoint(@Quoted String uri) {
        this.uri = uri;
        return this;
    }

    public GroovyConsoleControllerStage a_groovy_script_$script(@Quoted String script) {
        this.script = script;
        return this;
    }

    public GroovyConsoleControllerStage post_request_is_received() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(uri).contentType("text/x-groovy").param("script", script));
        if (script == null) {
            this.resultActions = resultActions;
        } else {
            this.resultActions = mockMvc.perform(asyncDispatch(resultActions.andExpect(request().asyncStarted()).andReturn()));
        }
        return this;
    }

    public GroovyConsoleControllerStage the_status_is(HttpStatus status) throws Exception {
        resultActions.andExpect(status().is(status.value()));
        return this;
    }

    public GroovyConsoleControllerStage the_content_is(String content) throws Exception {
        resultActions.andExpect(content().json(content));
        return this;
    }
}
