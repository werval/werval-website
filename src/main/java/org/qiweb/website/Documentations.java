/*
 * Copyright (c) 2014 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qiweb.website;

import org.qiweb.api.controllers.Classpath;
import org.qiweb.api.outcomes.Outcome;

import static org.qiweb.api.context.CurrentContext.outcomes;

/**
 * Documentations Controller.
 */
public class Documentations
{
    public Outcome developIndex()
    {
        return outcomes().seeOther( "/doc/develop/index.html" ).build();
    }

    public Outcome developApi()
    {
        return outcomes().seeOther( "/doc/develop/api/index.html" ).build();
    }

    public Outcome developModules()
    {
        return outcomes().seeOther( "/doc/develop/modules/index.html" ).build();
    }

    public Outcome developResource( String path )
    {
        return new Classpath().resource( "org/qiweb/website/develop", path );
    }

    public Outcome currentIndex()
    {
        return outcomes().seeOther( "/doc/current/index.html" ).build();
    }

    public Outcome currentApi()
    {
        return outcomes().seeOther( "/doc/current/api/index.html" ).build();
    }

    public Outcome currentModules()
    {
        return outcomes().seeOther( "/doc/current/modules/index.html" ).build();
    }

    public Outcome currentResource( String path )
    {
        return new Classpath().resource( "org/qiweb/website/current", path );
    }

    public Outcome versionsIndex( String version )
    {
        return outcomes().seeOther( "/doc/v/" + version + "/index.html" ).build();
    }

    public Outcome versionsApi( String version )
    {
        return outcomes().seeOther( "/doc/v/" + version + "/api/index.html" ).build();
    }

    public Outcome versionsModules( String version )
    {
        return outcomes().seeOther( "/doc/v/" + version + "/modules/index.html" ).build();
    }

    public Outcome versionsResource( String version, String path )
    {
        return new Classpath().resource( "org/qiweb/website/versions/" + version, path );
    }
}
