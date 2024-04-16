package dev.gaurav.testjpa.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gaurav.testjpa.DTO.CodeRunnerResponeDTO;
import dev.gaurav.testjpa.DTO.RunCode;
import dev.gaurav.testjpa.repositorys.QuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CodeRunnerService {
    private final QuestionRepository questionRepository;
    private final ObjectMapper jacksonObjectMapper;
    @Value("${jdoodle.clientId}")
    private String clientId;
    @Value("${jdoodle.clientSecret}")
    private String clientSecret;

    CodeRunnerService(QuestionRepository questionRepository, ObjectMapper jacksonObjectMapper) {
        this.questionRepository = questionRepository;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }
    public CodeRunnerResponeDTO runCode(RunCode code) throws IOException, InterruptedException {
        System.out.println(code.code());
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = """
                {
                    "clientId": "%s",
                    "clientSecret": "%s",
                    "stdin": "%s",
                    "script": "%s",
                    "language": "%s"
                }""".formatted(
                        this.clientId,
                        this.clientSecret,
                        code.defaultInputs().replace("\n", "\\n"),
                        code.code().replace("\n", "\\n").replace("\"", "\\\""),
                        code.language()
        );
        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.jdoodle.com/v1/execute"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Error in code execution");
        }
        System.out.println(response.body());
        return new ObjectMapper().readValue(response.body(), CodeRunnerResponeDTO.class);
    }
}