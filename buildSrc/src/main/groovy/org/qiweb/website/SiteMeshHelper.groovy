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
package org.qiweb.website

import java.nio.CharBuffer;
import org.sitemesh.builder.SiteMeshOfflineBuilder;
import org.sitemesh.offline.SiteMeshOffline;
import org.sitemesh.offline.directory.Directory;
import org.sitemesh.offline.directory.FileSystemDirectory;
import org.sitemesh.offline.directory.InMemoryDirectory;

final class SiteMeshHelper
{
    public static SiteMeshOffline createSiteMesh( String decoratorText, File sourceDir, File destinationDir )
    {
        def Directory source = new FileSystemDirectory( sourceDir )
        def Directory destination = new FileSystemDirectory( destinationDir )
        source.save( "decorator.html", CharBuffer.wrap( decoratorText.toCharArray() ) )
        new SiteMeshOfflineBuilder()
        .setSourceDirectory( source )
        .setDestinationDirectory( destination )
        .addDecoratorPath( "/*", "decorator.html" )
        .create()
    }
    
    private SiteMeshHelper()
    {
    }
}
