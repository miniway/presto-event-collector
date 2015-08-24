package presto.collector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventRequest
{
    private final String type;
    private final Object data;

    @JsonCreator
    public EventRequest(
            @JsonProperty("type") String type,
            @JsonProperty("uuid") String uuid,
            @JsonProperty("host") String host,
            @JsonProperty("timestamp") String timestamp,
            @JsonProperty("data") Object data)
    {
        this.type = type;
        this.data = data;
    }

    @JsonProperty
    public String getType()
    {
        return type;
    }

    @JsonProperty
    public Object getData()
    {
        return data;
    }

    public static class EventRequests
    {
        private final List<EventRequest> requests;

        @JsonCreator
        public EventRequests(List<EventRequest> requests)
        {
            this.requests = requests;
        }

        public List<EventRequest> getRequests()
        {
            return requests;
        }
    }
}
