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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class FrontTask extends DefaultTask
{
    @InputDirectory
    File inputDir = project.file( 'src/asciidoc' )

    @OutputDirectory
    File outputDir = project.file( 'build/generated-src/front/resources' )

    @TaskAction
    void docDevelop()
    {
        // Temporary directories
        def tmp = project.file( 'build/tmp/front')
		def tmpAsciidoced = new File( tmp, 'asciidoced' )
		def tmpSitemeshed = new File( tmp, 'sitemeshed' )
		tmpAsciidoced.mkdirs()
		tmpSitemeshed.mkdirs()

		// Asciidoctor
        project.fileTree( inputDir ).matching( { include "**/*.adoc" } ).visit { e ->
            if( e.file.isFile() ) {
            	def target = new File( tmpAsciidoced, e.relativePath.toString() )
            	target.getParentFile().mkdirs()
                AsciidocHelper.renderFile( e.file, inputDir, target.getParentFile() )
        	}
        }
        project.copy {
        	from inputDir
        	into tmpAsciidoced
        	exclude '**/*.adoc'
        }

        // SiteMesh to add menu
        def loader = project.classLoaderFor( project.website.doc_develop.name )

        // Mangle original decorator
        def decorator = loader.getResourceAsStream( "org/qiweb/doc/decorator.html" ).text
        //def parser = new org.cyberneko.html.parsers.SAXParser()
        def parser = new org.ccil.cowan.tagsoup.Parser()
        parser.setFeature( "http://xml.org/sax/features/namespaces", false )
        parser.setFeature( "http://xml.org/sax/features/validation", false )
        parser.setFeature( "http://xml.org/sax/features/external-general-entities", false )
        parser.setFeature( "http://xml.org/sax/features/external-parameter-entities", false )
        parser.setFeature( "http://www.ccil.org/~cowan/tagsoup/features/cdata-elements", false )
        def gDecorator = new XmlSlurper( parser ).parseText( decorator )
        // Change jquery script tag src
        def gDecoratorJQuery = gDecorator.head.script.find { it.@src.toString().contains( 'jquery' ) }
        gDecoratorJQuery.replaceNode { node ->
            script( src: '/doc/develop/javascripts/jquery-1.9.1.min.js', type: 'text/javascript' ) {}
        }
        // Change menu links
        def gDecoratorHeader = gDecorator.body.depthFirst().findAll { "qiweb-doc-header".equals( it.@id.toString() ) }
        gDecoratorHeader.nav[0].ul[0].replaceNode { node ->
            ul() {
                li() {
                    a( href:'/index.html' ) {
                        img( src: '/doc/develop/images/logo.png', alt: 'logo' )
                        strong( 'qiweb' )
                    }
                }
                li() { a( 'Overview', href:'/overview.html', 'class': 'qiweb-doc-link' ) }
                li() { a( 'News', href:'/news.html', 'class': 'qiweb-doc-link' ) }
                li() { a( 'Downloads', href:'/downloads.html', 'class': 'qiweb-doc-link' ) }
                li() { a( 'Documentation', href:'/doc/index.html', 'class': 'qiweb-doc-link' ) }
                li() { a( 'Community', href:'/community.html', 'class': 'qiweb-doc-link' ) }
                li() { a( 'Security', href:'/security.html', 'class': 'qiweb-doc-link' ) }
            }
        }
        def String modifiedDecorator = new groovy.xml.StreamingMarkupBuilder().bind { mkp.yield gDecorator }
        // That's some dirty hack for & inside <script/> that gets replaced to html entities
        modifiedDecorator = "<!doctype html>\n" + modifiedDecorator.replaceAll( '&amp;', '&' )
        def sm = SiteMeshHelper.createSiteMesh( modifiedDecorator, tmpAsciidoced, tmpSitemeshed  )
        project.fileTree( tmpAsciidoced ).matching( { include '**/*.html'; exclude 'decorator.html' } ).visit { e ->
            if( e.file.isFile() ) {
                sm.process( e.relativePath.toString() )
            }
        }
        project.copy {
            from tmpAsciidoced
            into tmpSitemeshed
            exclude '**/*.html'
        }
        
        // Put the result in build/generated-src and register in into the project
		project.copy {
			from tmpSitemeshed
			into new File( outputDir, 'org/qiweb/website/front' )
		}

        // Cleanup
		// project.delete tmp
    }
}
