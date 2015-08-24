package presto.collector;

import com.google.inject.Binder;
import io.airlift.configuration.ConfigurationAwareModule;
import io.airlift.configuration.ConfigurationFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.airlift.discovery.client.DiscoveryBinder.discoveryBinder;
import static io.airlift.jaxrs.JaxrsBinder.jaxrsBinder;
import static io.airlift.json.JsonCodecBinder.jsonCodecBinder;

public class CollectorMainModule
        implements ConfigurationAwareModule
{
    private ConfigurationFactory configurationFactory;
    private Binder binder;

    @Override
    public void setConfigurationFactory(ConfigurationFactory configurationFactory)
    {
        this.configurationFactory = checkNotNull(configurationFactory, "configurationFactory is null");
    }

    @Override
    public synchronized void configure(Binder binder)
    {
        this.binder = binder;
        discoveryBinder(binder).bindHttpAnnouncement("collector");

        jsonCodecBinder(binder).bindJsonCodec(EventRequest.class);
        jaxrsBinder(binder).bind(EventResource.class);
    }
}
