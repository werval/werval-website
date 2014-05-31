<#include "header.ftl">
	
	<#include "menu.ftl">

	<div class="page-header">
		<h1><#escape x as x?xml>${content.title}</#escape></h1>
	</div>

	<p>${content.body}</p>

	<#assign seq = ["0.2.0.Alpha", "0.1.3.Alpha"]>
	<#list seq as version>
	  	<div class="sect2">
	 		<h3 id="${version}">${version}</h3>
	 		<div class="paragraph"><p>Documentation for QiWeb ${version} can be found <a href="${version}/index.html">here</a>.</p></div>
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

<#include "footer.ftl">
