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

import org.gradle.api.Plugin
import org.gradle.api.Project

class WebSitePlugin implements Plugin<Project>
{
    void apply( Project project )
    {
        project.apply plugin: com.smokejumperit.gradle.ClassLoadersPlugin

        project.extensions.create( "website", WebSiteExtension )

        project.task(
            "front",
            type: FrontTask,
            group:"QiWeb WebSite",
            description: 'Generates /'
        )

        project.task(
            "docDevelop",
            type: DocDevelopTask,
            group:"QiWeb WebSite",
            description: 'Generates /doc/develop'
        )

        project.task(
            "docCurrent",
            type: DocCurrentTask,
            group:"QiWeb WebSite",
            description: 'Extract /doc/current'
        )

        project.task(
            "docVersions",
            type: DocVersionsTask,
            group:"QiWeb WebSite",
            description: 'Extract /doc/:version'
        )

        project.tasks.getByName( "processResources" ).dependsOn(
            project.tasks.getByName( "docDevelop" ),
            project.tasks.getByName( "docCurrent" ),
            project.tasks.getByName( "docVersions" ),
            project.tasks.getByName( "front" )
        )
    }
}

