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
import org.asciidoctor.Asciidoctor
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.classloader.ClasspathUtil
import org.sitemesh.builder.SiteMeshOfflineBuilder;
import org.sitemesh.offline.SiteMeshOffline;
import org.sitemesh.offline.directory.Directory;
import org.sitemesh.offline.directory.FileSystemDirectory;
import org.sitemesh.offline.directory.InMemoryDirectory;

class DocDevelopTask extends DefaultTask
{
    @OutputDirectory
    File outputDir = project.file( 'build/generated-src/develop/resources' )

    @TaskAction
    void docDevelop()
    {
        // Temporary directories
        def tmp = project.file( 'build/tmp/doc_develop')
        def tmpAggregated = new File( tmp, 'aggregated' )
        def tmpSitemeshed = new File( tmp, 'sitemeshed' )
        tmpAggregated.mkdirs()
        tmpSitemeshed.mkdirs()
        
        // Develop ClassLoader
        def loader = project.classLoaderFor( project.website.doc_develop.name )

        // Core Documentation
        def coreDocJar = ClasspathUtil.getClasspathForResource( loader, "org/qiweb/doc/html/index.html" )
        def coreDocJarTree = project.zipTree( coreDocJar ).matching { include "org/qiweb/doc/html/**" }
        def coreDocTmp = new File( tmp, 'core' )
        project.copy {
            from coreDocJarTree
            into coreDocTmp
        }
        project.copy {
            from new File( coreDocTmp, "org/qiweb/doc/html" )
            into tmpAggregated
        }
        project.delete coreDocTmp

        // Dynamic Modules Documentations
        // Parse configuration
        def config = com.typesafe.config.ConfigFactory.load( loader )
        def dyndocsConfig = config.getConfig( 'qiweb.devshell.dyndocs' )
        def List<DynDoc> dyndocs = []
        dyndocsConfig.root().keySet().each { id ->
            def dyndocConfig = dyndocsConfig.getConfig( id )
            def DynDoc dyndoc = new DynDoc()
            dyndoc.id = id
            dyndoc.name = dyndocConfig.hasPath( 'name' ) ? dyndocConfig.getString( 'name' ) : id
            dyndoc.base_path = dyndocConfig.hasPath( 'base_path' ) ? dyndocConfig.getString( 'base_path' ) : id
            dyndoc.entry_point = dyndocConfig.hasPath( 'entry_point' ) ? dyndocConfig.getString( 'entry_point' ) : 'index.html'

            dyndocs += dyndoc
        }

        // Extract module documentation
        dyndocs.each { dyndoc ->
            def dyndocJar = ClasspathUtil.getClasspathForResource( loader, "$dyndoc.base_path/$dyndoc.entry_point" )
            def dyndocJarTree = project.zipTree( dyndocJar ).matching { include "$dyndoc.base_path/**" }

            def dyndocTmp = new File( tmp, 'dyndocs' )
            def dyndocOutputDir = new File( tmpAggregated, "modules/$dyndoc.id" )
            project.copy {
                from dyndocJarTree
                into dyndocTmp
            }
            project.copy {
                from new File( dyndocTmp, dyndoc.base_path )
                into dyndocOutputDir
            }
            project.delete dyndocTmp
            // println "$dyndoc.name ($dyndoc.id) -> $dyndocOutputDir"
        }

        // Generate Modules Index
        def modulesIndexTmp = new File( tmp, 'modulesIndex' )
        def modulesIndex =
"""= Modules
Reusable bits for your Applications
:title: QiWeb Modules
:description: Reusable bits for your Applications
:keywords: qiweb, documentation, module, plugin
:toc: right

Modules contains non-core functionnality.

Modules are simple JARs and can contain controllers, utility classes and QiWeb Plugins.

== Official Modules

The QiWeb team maintains a collection of documented, tested modules that evolve along the core SDK.

"""
        dyndocs.sort( { a,b -> a.name <=> b.name } ).each { dyndoc ->
            modulesIndex += """
=== $dyndoc.name

    compile 'org.qiweb.modules.$dyndoc.id:0'

link:$dyndoc.id/$dyndoc.entry_point[$dyndoc.name Documentation]

"""
        }
        modulesIndex += """
== Community Modules

NOTE: Community Modules are maintained by the community.
It's the community responsibility to keep them documented, tested and up to date.
The QiWeb Team makes no guarantee regarding their shape.
Check their activity and codebase before using in production.

"""
        def modulesIndexInput = new File( modulesIndexTmp, 'input' )
        def modulesIndexOutput = new File( modulesIndexTmp, 'output' )
        modulesIndexInput.mkdirs()
        modulesIndexOutput.mkdirs()
        def modulesIndexFile = new File( modulesIndexInput, 'index.adoc' )
        modulesIndexFile.write modulesIndex
        // Workaround for https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/61
        System.setProperty('jruby.logger.class','org.jruby.util.log.StandardErrorLogger')
        Asciidoctor.Factory.create().renderFile(
            modulesIndexFile,
            [
                'in_place': false,
                'safe': 0,
                'base_dir': modulesIndexInput.absolutePath,
                'to_dir': modulesIndexOutput.absolutePath,
                'backend': 'html5',
                'attributes':[
                    'sectlink': '',
                    'sectanchors':'',
                    'linkattrs': '',
                    'linkcss': '',
                    'source-highlighter': 'coderay', 'coderay-css': 'class',
                ]
            ]
        )
        project.copy {
            from modulesIndexOutput
            into new File( tmpAggregated, "modules/" )
        }
        project.delete modulesIndexTmp

        // SiteMesh the whole thing
        def Directory source = new FileSystemDirectory( tmpAggregated );
        def Directory destination = new FileSystemDirectory( tmpSitemeshed )
        def decorator = loader.getResourceAsStream( "org/qiweb/doc/decorator.html" ).text
        decorator = decorator.replaceAll( "/@doc", "/doc/develop" )
        source.save( "decorator.html", CharBuffer.wrap( decorator.toCharArray() ) )

        SiteMeshOffline sitemesh = new SiteMeshOfflineBuilder()
        .setSourceDirectory( source )
        .setDestinationDirectory( destination )
        .addDecoratorPath( "/*", "decorator.html" )
        .create()

        project.fileTree( tmpAggregated ).matching( { include "**/*.html"; exclude "api/**/*.*" } ).visit { e ->
            if( e.file.isFile() ) {
                sitemesh.process( e.relativePath.toString() )
            }
        }
        project.copy {
            from tmpAggregated
            into tmpSitemeshed
            exclude '**/*.html'
            exclude 'api/**/*.*'
        }
        project.copy {
            from tmpAggregated
            into tmpSitemeshed
            include 'api/**/*.*'
        }

        // Put the result in build/generated-src and register in into the project
        project.copy {
            from tmpSitemeshed
            into new File( outputDir, "org/qiweb/website/develop" )
        }

        // Cleanup
        project.delete tmp
    }

    class DynDoc {
        def id
        def name
        def base_path
        def entry_point
    }
}
