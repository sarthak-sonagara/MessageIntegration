import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/graphql")
public class GraphqlController {
    private final GraphqlService graphqlService;

    @Autowired
    public GraphqlController(GraphqlService graphqlService) {
        this.graphqlService = graphqlService;
    }

    @GetMapping("/free-text-search")
    public ResponseEntity<Object> freeTextSearch(@RequestBody String query){
        return new ResponseEntity<Object>(graphqlService.getFreeTextSearchResult(query), HttpStatus.OK);
    }
}
