package upload;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class UploadMutation implements GraphQLMutationResolver {

  boolean upload(Part unusedPart, DataFetchingEnvironment environment) {
    Part part = environment.getArgument("file");
    log.info("Uploaded file: {}", part);
    return true;
  }

}
