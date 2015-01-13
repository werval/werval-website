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
                        <div class="paragraph">
                            <p><a href="${version}/index.html" class="btn btn-sm btn-warning">Documentation for ${version}</a></p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </#if>

<#include "footer.ftl">
