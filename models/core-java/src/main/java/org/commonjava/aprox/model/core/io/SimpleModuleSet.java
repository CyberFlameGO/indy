/**
 * Copyright (C) 2011 Red Hat, Inc. (jdcasey@commonjava.org)
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
package org.commonjava.aprox.model.core.io;

import com.fasterxml.jackson.databind.Module;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jdcasey on 8/26/15.
 */
public class SimpleModuleSet
        implements ModuleSet
{

    private Set<Module> modules;

    public SimpleModuleSet( Module... modules )
    {
        this.modules = new HashSet<>( Arrays.asList( modules ) );
    }

    @Override

    public Set<Module> getModules()
    {
        return modules;
    }
}
