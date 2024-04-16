package dev.gaurav.testjpa.controllers;

import dev.gaurav.testjpa.DTO.CodeRunnerResponeDTO;
import dev.gaurav.testjpa.DTO.RunCode;
import dev.gaurav.testjpa.services.CodeRunnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/code")
@CrossOrigin
public class CodeRunnerController {
    private final CodeRunnerService codeRunnerService;

    public CodeRunnerController(CodeRunnerService codeRunnerService) {
        this.codeRunnerService = codeRunnerService;
    }

    @PostMapping("/run")
    public ResponseEntity<CodeRunnerResponeDTO> runCode(@RequestBody RunCode code) {
        try {
            return ResponseEntity.ok(codeRunnerService.runCode(code));
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}