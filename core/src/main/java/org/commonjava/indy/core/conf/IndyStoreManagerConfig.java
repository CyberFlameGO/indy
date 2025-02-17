/**
 * Copyright (C) 2011-2020 Red Hat, Inc. (https://github.com/Commonjava/indy)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.indy.core.conf;

import org.commonjava.indy.conf.IndyConfigInfo;
import org.commonjava.propulsor.config.annotation.ConfigName;
import org.commonjava.propulsor.config.annotation.SectionName;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;

@SectionName("store-manager")
@ApplicationScoped
public class IndyStoreManagerConfig implements IndyConfigInfo
{

    private String keyspace;

    private int replicationFactor;

    public IndyStoreManagerConfig() {}

    public IndyStoreManagerConfig( String keyspace, int replicationFactor )
    {
        this.keyspace = keyspace;
        this.replicationFactor = replicationFactor;
    }

    public String getKeyspace()
    {
        return keyspace;
    }

    @ConfigName( "store.manager.keyspace" )
    public void setKeyspace( String keyspace )
    {
        this.keyspace = keyspace;
    }

    @Override
    public String getDefaultConfigFileName()
    {
        return "conf.d/store-manager.conf";
    }

    @Override
    public InputStream getDefaultConfig()
    {
        return Thread.currentThread()
                     .getContextClassLoader()
                     .getResourceAsStream( "default-store-manager.conf" );
    }
}
