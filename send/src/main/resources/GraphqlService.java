import com.sms.send.data.DataService;
import com.sms.send.data.entities.UniversalMessage;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static graphql.schema.FieldCoordinates.coordinates;

@Service
public class GraphqlService {
    private final DataService dataService;

    @Autowired
    public GraphqlService(DataService dataService) {
        this.dataService = dataService;
    }

    public Object getFreeTextSearchResult(String query){

        GraphQLObjectType universalMessageType = GraphQLObjectType.newObject()
                .name("UniversalMessage")
                .field(field -> field
                        .name("content")
                        .type(Scalars.GraphQLString))
                .field(field -> field
                        .name("source")
                        .type(Scalars.GraphQLString))
                .build();

        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query")
                .field(field -> field
                        .name("freeTextSearch")
                        .argument(arg -> arg
                                .name("input")
                                .type(Scalars.GraphQLString))
                        .type(new GraphQLList(universalMessageType)))
                .build();
        DataFetcher<List<UniversalMessage>> messageDataFetcher = new DataFetcher<List<UniversalMessage>>() {
            @Override
            public List<UniversalMessage> get(DataFetchingEnvironment environment) throws Exception {
                return dataService.getFreeTextSearchResult(environment.getArgument("input"));
            }
        };

        GraphQLCodeRegistry codeRegistry = GraphQLCodeRegistry
                .newCodeRegistry()
                .dataFetcher(coordinates(queryType,"freeTextSearch"),messageDataFetcher)
                .build();

        GraphQLSchema schema = GraphQLSchema
                .newSchema()
                .query(queryType)
                .codeRegistry(codeRegistry)
                .build();

        GraphQL graphQL = GraphQL
                .newGraphQL(schema)
                .build();

        ExecutionInput executionInput = ExecutionInput
                .newExecutionInput()
                .query(query)
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);

        Object data = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();
        if(errors.isEmpty())
            return data;
        return null;
    }
}
