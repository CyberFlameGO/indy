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
package org.commonjava.indy.promote.ftest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commonjava.indy.client.core.IndyClientModule;
import org.commonjava.indy.ftest.core.AbstractIndyFunctionalTest;
import org.commonjava.indy.promote.client.IndyPromoteAdminClientModule;
import org.commonjava.indy.promote.ftest.AbstractPromotionFunctionalTest;
import org.commonjava.indy.promote.model.ValidationRuleSet;
import org.commonjava.indy.test.fixture.core.CoreServerFixture;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractAdminValidationTest
        extends AbstractPromotionFunctionalTest
{
    protected IndyPromoteAdminClientModule module;

    @Override
    public void start()
            throws Throwable
    {
        super.start();
        module = client.module( IndyPromoteAdminClientModule.class );
    }

    @Override
    protected void initTestData( CoreServerFixture fixture )
            throws IOException
    {
        super.initTestData( fixture );

        getRuleScriptFiles().forEach( ( k, v ) -> {
            try
            {
                deployRule( k, v );
            }
            catch ( IOException e )
            {
                logger.error( e.getMessage(), e );
            }
        } );

        Map<String, ValidationRuleSet> sets = getRuleSets();
        if ( sets != null && !sets.isEmpty() )
        {
            sets.forEach( ( name, set ) -> {
                try
                {
                    deployRuleSet( name, set );
                }
                catch ( IOException e )
                {
                    logger.error( e.getMessage(), e );
                }
            } );

        }


    }

    protected void deployRule( String ruleName, String ruleContent )
            throws IOException
    {
        String fileName = ruleName.endsWith( ".groovy" ) ? ruleName : ruleName + ".groovy";
        String rulePath = "rules/" + fileName;
        deleteRule( fileName );
        writePromoteDataFile( rulePath, ruleContent );
    }

    protected void deployRuleSet( String fileName,  ValidationRuleSet ruleSet )
            throws IOException
    {
        String json = new ObjectMapper().writeValueAsString( ruleSet );
        String ruleSetFileName = fileName.endsWith( ".json" ) ? fileName : fileName + ".json";
        String rulesetPath = "rule-sets/" + ruleSetFileName;
        deleteRuleSet( ruleSetFileName );
        writePromoteDataFile( rulesetPath, json );
    }

    protected boolean deleteRule( String ruleName )
    {
        String fileName = ruleName.endsWith( ".groovy" ) ? ruleName : ruleName + ".groovy";
        return new File( promoteDataDir, "rules/" + fileName ).delete();
    }

    protected boolean deleteRuleSet( String ruleSetFileName )
    {
        String fileName = ruleSetFileName.endsWith( ".json" ) ? ruleSetFileName : ruleSetFileName + ".json";
        return new File( promoteDataDir, "rule-sets/" + fileName ).delete();
    }

    protected abstract Map<String, String> getRuleScriptFiles()
            throws IOException;

    protected abstract Map<String, ValidationRuleSet> getRuleSets()
            throws IOException;

    @Override
    protected Collection<IndyClientModule> getAdditionalClientModules()
    {
        return Collections.singletonList( new IndyPromoteAdminClientModule() );
    }

    @Override
    protected void initTestConfig( CoreServerFixture fixture )
            throws IOException
    {
        super.initTestConfig( fixture );
        writeConfigFile( "conf.d/threadpools.conf", "[threadpools]\nenabled=true" );
    }

}
