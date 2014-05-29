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
 * {@literal /doc/:version} Controller.
 */
public class DocVersions
{
    public Outcome index( String version )
    {
        return outcomes().seeOther( "/doc/" + version + "/index.html" ).build();
    }

    public Outcome api( String version )
    {
        return outcomes().seeOther( "/doc/" + version + "/api/index.html" ).build();
    }

    public Outcome modules( String version )
    {
        return outcomes().seeOther( "/doc/" + version + "/modules/index.html" ).build();
    }

    public Outcome resource( String version, String path )
    {
        return new Classpath().resource( "org/qiweb/website/versions/" + version, path );
    }
}
