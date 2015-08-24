package presto.collector;

import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.discovery.client.Announcer;
import io.airlift.discovery.client.DiscoveryModule;
import io.airlift.event.client.EventModule;
import io.airlift.http.server.HttpServerModule;
import io.airlift.jaxrs.JaxrsModule;
import io.airlift.json.JsonModule;
import io.airlift.log.Logger;
import io.airlift.node.NodeModule;

public class PrestoCollector
        implements Runnable
{
    private static final Logger log = Logger.get(PrestoCollector.class);

    @Override
    public void run()
    {
        Bootstrap app = new Bootstrap(ImmutableList.of(
                new NodeModule(),
                new DiscoveryModule(),
                new HttpServerModule(),
                new JsonModule(),
                new JaxrsModule(true),
                new EventModule(),

                new CollectorMainModule()
        ));

        try {
            Injector injector = app.strictConfig().initialize();

            injector.getInstance(Announcer.class).start();

            log.info("======== Collector STARTED ========");
        }
        catch (Throwable e) {
            log.error(e);
            System.exit(1);
        }
    }

    public static void main(String[] args)
    {
        new PrestoCollector().run();
    }
}
