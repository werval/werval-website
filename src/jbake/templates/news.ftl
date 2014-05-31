<#include "header.ftl">
	
	<#include "menu.ftl">

	<div class="page-header">
		<a class="btn btn-xs btn-warning pull-right" href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>${config.feed_file}">
			<i class="glyphicon glyphicon-paperclip"></i>
		</a>
		<h1><#escape x as x?xml>${content.title}</#escape></h1>
	</div>

	<p>${content.body}</p>

	<hr />

	<#list posts as post>
  		<#if (post.status == "published")>
  			<a href="${post.uri}"><h1><#escape x as x?xml>${post.title}</#escape></h1></a>
  			<p>${post.date?string("dd MMMM yyyy")}</p>
  			<p>${post.body}</p>
  		</#if>
  	</#list>
	
	<p>Older news are available in the <a href="${config.archive_file}">archive</a>.</p>

<#include "footer.ftl">
