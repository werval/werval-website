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
                    </div>
                </#list>
            </div>
        </div>
    </#if>

<#include "footer.ftl">
