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

import org.asciidoctor.Asciidoctor
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
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
		tmp.mkdirs()

		// Asciidoctor
		def tmpAsciidoced = new File( tmp, 'asciidoced' )
		tmpAsciidoced.mkdirs()
        // Workaround for https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/61
        System.setProperty('jruby.logger.class','org.jruby.util.log.StandardErrorLogger')
        Asciidoctor asciidoctor = Asciidoctor.Factory.create()
        project.fileTree( inputDir ).matching( { include "**/*.adoc" } ).visit { e ->
            if( e.file.isFile() ) {
            	def target = new File( tmpAsciidoced, e.relativePath.toString() )
            	target.getParentFile().mkdirs()
		        asciidoctor.renderFile(
		            e.file,
		            [
		                'in_place': false,
		                'safe': 0,
		                'base_dir': inputDir.absolutePath,
		                'to_dir': target.getParentFile().absolutePath,
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
        	}
        }
        project.copy {
        	from inputDir
        	into tmpAsciidoced
        	exclude '**/*.adoc'
        }

        // TODO SiteMesh to add menu

        // Finalize
		project.copy {
			from tmpAsciidoced
			into new File( outputDir, 'org/qiweb/website/front' )
		}
		project.delete tmp
    }
}
