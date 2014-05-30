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

final class AsciidocHelper
{
    static {
        // Workaround for https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/61
        System.setProperty('jruby.logger.class','org.jruby.util.log.StandardErrorLogger')
    }
    private static final Asciidoctor ASCIIDOCTOR = Asciidoctor.Factory.create()

    public static renderFile( file, File base_dir, File to_dir )
    {
        ASCIIDOCTOR.renderFile(
            file,
            [
                'in_place': false,
                'safe': 0,
                'base_dir': base_dir.absolutePath,
                'to_dir': to_dir.absolutePath,
                'backend': 'html5',
                'attributes':[
                    'sectlink': '',
                    'sectanchors':'',
                    'linkattrs': '',
                    'linkcss': '',
                    'source-highlighter': 'coderay', 'coderay-css': 'class',
                ] + []
            ]
        )
    }

    private AsciidocHelper()
    {
    }
}
