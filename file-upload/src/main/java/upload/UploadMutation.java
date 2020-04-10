package upload;

import graphql.kickstart.tools.GraphQLMutationResolver;
import java.io.IOException;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class UploadMutation implements GraphQLMutationResolver {

  boolean upload(Part part) throws IOException {
    log.info("Part: {}", part.getSubmittedFileName());
    part.write(part.getSubmittedFileName());
    return true;
  }

}
