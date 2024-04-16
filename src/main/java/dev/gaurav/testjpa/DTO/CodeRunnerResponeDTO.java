package dev.gaurav.testjpa.DTO;

public record CodeRunnerResponeDTO(
        String output,
        int statusCode,
        String memory,
        String cpuTime,
        byte compilationStatus,
        String projectKey
) {
}
