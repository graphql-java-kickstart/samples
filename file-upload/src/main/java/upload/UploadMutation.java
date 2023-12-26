package upload;

import graphql.kickstart.tools.GraphQLMutationResolver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class UploadMutation implements GraphQLMutationResolver {

  Map<String, String> upload(Part part) throws IOException {
    Map<String, String> uploadedFile = Map.of(
      "filename", part.getSubmittedFileName(),
      "type", part.getContentType(),
      "content", new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
    );
    log.info("File uploaded: {}", uploadedFile);
    return uploadedFile;
  }
}
