package org.commonjava.aprox.core.rest.stats;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.UriBuilder;

import org.commonjava.aprox.core.rest.RESTApplication;
import org.commonjava.aprox.model.ArtifactStore;

import com.google.gson.annotations.SerializedName;

public final class EndpointView
    implements Comparable<EndpointView>
{
    private static final ApplicationPath APP_PATH = RESTApplication.class.getAnnotation( ApplicationPath.class );

    private final String name;

    private final String type;

    @SerializedName( "resource_uri" )
    private final String resourceUri;

    public EndpointView( final ArtifactStore store, final UriBuilder uriBuilder )
    {
        this.name = store.getName();

        this.type = store.getDoctype()
                         .name();

        this.resourceUri = uriBuilder.replacePath( "" )
                                     .path( APP_PATH.value() )
                                     .path( store.getDoctype()
                                                 .singularEndpointName() )
                                     .path( store.getName() )
                                     .build()
                                     .toString();
    }

    public final String getName()
    {
        return name;
    }

    public final String getType()
    {
        return type;
    }

    public final String getResourceURI()
    {
        return resourceUri;
    }

    public final String getKey()
    {
        return type + ":" + name;
    }

    @Override
    public int compareTo( final EndpointView point )
    {
        return getKey().compareTo( point.getKey() );
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( resourceUri == null ) ? 0 : resourceUri.hashCode() );
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final EndpointView other = (EndpointView) obj;
        if ( resourceUri == null )
        {
            if ( other.resourceUri != null )
            {
                return false;
            }
        }
        else if ( !resourceUri.equals( other.resourceUri ) )
        {
            return false;
        }
        return true;
    }
}