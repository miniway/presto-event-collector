package presto.collector;

import presto.collector.EventRequest.EventRequests;
import io.airlift.log.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.common.net.HttpHeaders.USER_AGENT;

@Path("/v2/event")
public class EventResource
{
    private static final Logger log = Logger.get(EventResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createQuery(
            EventRequests events,
            @HeaderParam(USER_AGENT) String nodeId)
    {
        for (EventRequest event : events.getRequests()) {
            if (event.getType().equals("HttpRequest")) {
                continue;
            }
            log.debug("%s %s", event.getType(), event.getData());
        }
        return Response.noContent().build();
    }
}
