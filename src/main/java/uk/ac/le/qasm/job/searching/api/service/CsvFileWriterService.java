package uk.ac.le.qasm.job.searching.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CsvFileWriterService {

    private final ObjectMapper objectMapper;

    public CsvFileWriterService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public byte[] generate(Object object) {
        try {
            String stringData = objectMapper.writeValueAsString(object);
            JsonNode data = objectMapper.readTree(stringData);

            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            data.elements().next().fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            CsvMapper csvMapper = new CsvMapper();

            return csvMapper.writerFor(JsonNode.class)
                            .with(csvSchema)
                            .writeValueAsBytes(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
