package org.commonjava.indy.subsys.template.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commonjava.indy.content.IndyLocationExpander;
import org.commonjava.indy.content.IndyPathGenerator;
import org.commonjava.indy.data.StoreDataManager;
import org.commonjava.indy.mem.data.MemoryStoreDataManager;
import org.commonjava.indy.model.core.io.IndyObjectMapper;
import org.commonjava.indy.model.galley.IndyLocationResolver;
import org.commonjava.maven.galley.cache.FileCacheProvider;
import org.commonjava.maven.galley.config.TransportManagerConfig;
import org.commonjava.maven.galley.event.NoOpFileEventManager;
import org.commonjava.maven.galley.io.NoOpTransferDecorator;
import org.commonjava.maven.galley.nfc.MemoryNotFoundCache;
import org.commonjava.maven.galley.spi.cache.CacheProvider;
import org.commonjava.maven.galley.spi.event.FileEventManager;
import org.commonjava.maven.galley.spi.nfc.NotFoundCache;
import org.commonjava.maven.galley.transport.htcli.Http;
import org.junit.rules.TemporaryFolder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * Created by jdcasey on 11/17/16.
 */
@ApplicationScoped
public class TestProvider
{

    @Inject
    private IndyPathGenerator indyPathGenerator;

    private NotFoundCache nfc;

    private StoreDataManager storeDataManager;

    private ObjectMapper objectMapper;

    private CacheProvider cacheProvider;

    private FileEventManager fileEventManager;

    private NoOpTransferDecorator transferDecorator;

    private TransportManagerConfig transportManagerConfig;

    private TemporaryFolder temp;

//    private Http http;
//
//    private IndyLocationExpander locationExpander;
//
//    private IndyLocationResolver locationResolver;

    @PostConstruct
    public void setup()
    {
        storeDataManager = new MemoryStoreDataManager( true );
        nfc = new MemoryNotFoundCache();
        objectMapper = new IndyObjectMapper( false );
        fileEventManager = new NoOpFileEventManager();
        transferDecorator = new NoOpTransferDecorator();
        transportManagerConfig = new TransportManagerConfig();

        temp = new TemporaryFolder();
        try
        {
            temp.create();

            cacheProvider =
                    new FileCacheProvider( temp.newFolder( "storage" ), indyPathGenerator, fileEventManager,
                                           transferDecorator );
        }
        catch ( IOException e )
        {
            fail( "Cannot initialize temporary directory structure" );
            temp.delete();
        }
    }

    @PreDestroy
    public void teardown()
    {
        if ( temp != null )
        {
            temp.delete();
        }
    }

    @Produces
    public NotFoundCache getNfc()
    {
        return nfc;
    }

    @Produces
    public StoreDataManager getStoreDataManager()
    {
        return storeDataManager;
    }

    @Produces
    public ObjectMapper getObjectMapper()
    {
        return objectMapper;
    }

    @Produces
    public CacheProvider getCacheProvider()
    {
        return cacheProvider;
    }

    @Produces
    public FileEventManager getFileEventManager()
    {
        return fileEventManager;
    }

    @Produces
    public NoOpTransferDecorator getTransferDecorator()
    {
        return transferDecorator;
    }

    @Produces
    public TransportManagerConfig getTransportManagerConfig()
    {
        return transportManagerConfig;
    }
}
