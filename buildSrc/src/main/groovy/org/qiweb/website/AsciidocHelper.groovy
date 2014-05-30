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
                ]
            ]
        )
    }

    private AsciidocHelper()
    {
    }
}
