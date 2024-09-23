package patrimoine.hei.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import patrimoine.hei.Model.Patrimoine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
@Service
public class PatrimoineService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path path = Paths.get("patrimoines");

    public PatrimoineService() throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    public Patrimoine getPatrimoine(String id) throws IOException {
        Path filePath = path.resolve(id + ".json");
        if (!Files.exists(filePath)) {
            return null;
        }
        return objectMapper.readValue(Files.readString(filePath), Patrimoine.class);
    }

    public void saveOrUpdatePatrimoine(String id, Patrimoine patrimoine) throws IOException {
        patrimoine.setDerniereModification(LocalDateTime.now());
        String json = objectMapper.writeValueAsString(patrimoine);
        Files.writeString(path.resolve(id + ".json"), json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
