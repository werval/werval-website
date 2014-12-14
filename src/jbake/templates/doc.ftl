<#include "header.ftl">
	
	<#include "menu.ftl">

	<div class="page-header">
		<h1><#escape x as x?xml>${content.title}</#escape></h1>
	</div>

	<p>${content.body}</p>

    <#if config.werval_previous_versions?has_content>
        <div class="sect1">
            <h2 id="previous_releases"><a class="anchor" href="#previous_releases"></a>Previous releases</h2>
            <div class="sectionbody">
                <#list config.werval_previous_versions as version>
                    <div class="sect2">
                        <h3 id="${version}">${version}</h3>
                        <div class="paragraph"><p>Documentation for Werval ${version} can be found <a href="${version}/index.html">here</a>.</p></div>
                        <div class="paragraph"><p>Don't forget to read the <a href="${version}/release-notes.html">release notes</a>.</p></div>
                        <div class="ulist">
                            <ul>
                                <li><p><a href="${version}/getting-started.html">Getting Started</a></p></li>
                                <li><p><a href="${version}/guides.html">Guides</a></p></li>
                                <li><p><a href="${version}/manual.html">Manual</a></p></li>
                                <li><p><a href="${version}/api/index.html">API Javadoc</a></p></li>
                                <li><p><a href="${version}/modules/index.html">Modules</a></p></li>
                                <li><p><a href="${version}/faq.html">FAQ</a></p></li>
                            </ul>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </#if>

<#include "footer.ftl">
