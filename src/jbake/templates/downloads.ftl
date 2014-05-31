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
	 		<div class="paragraph"><p>Downloads for QiWeb ${version} can be found below.</p></div>
	 		<div class="ulist">
	 			<ul>
	 				<li><p><a href="/downloads/${version}/distribution">Distribution</a></p></li>
	 				<li><p><a href="/downloads/${version}/sources">Sources</a></p></li>
	 				<li><p><a href="/downloads/${version}/documentation">Documentation</a></p></li>
	 			</ul>
	 		</div>
	 	</div>
	</#list>

<#include "footer.ftl">
