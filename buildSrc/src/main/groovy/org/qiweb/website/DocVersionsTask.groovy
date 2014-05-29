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
import org.gradle.api.GradleException
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class DocVersionsTask extends DefaultTask
{
    @OutputDirectory
    File outputDir = project.file( 'build/generated-src/versions/resources' )

    @TaskAction
    void docVersions()
    {
        project.website.doc_versions.each { version, config ->
            new File( outputDir, "org/qiweb/website/versions/" + version ).mkdirs()
            new File( outputDir, "org/qiweb/website/versions/" + version + "/index.html" ).write "<!doctype html><html><body><h1>TODO</h1></body></html>"
        }
    }
}
