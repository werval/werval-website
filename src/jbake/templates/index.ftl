<#include "header.ftl">
	
	<#include "menu.ftl">

	<div class="page-header">
		<h1><img src="images/logo.png" style="width: 80px; height: 80px;"/> Werval</h1>
        <div class="muted">Zen and Energy for Web Development</div>
	</div>

	<div id="preamble">
		<div class="sectionbody">
			<div class="paragraph">
				<p>
                    Werval is an open source JVM based toolkit for building HTTP services.
                    A lightweight core, live reload dev mode, useful modules, plugins for several build tools and
                    multiple languages support leave you free to choose the right architecture for your domain.
                </p>
			</div>
            <div style="text-align:center">
                <p>
                    <div class="btn-group">
                      <a href="doc/index.html" class="btn btn-lg btn-primary"><i class="glyphicon glyphicon-book"></i> Learn more</a>
                      <button type="button" class="btn btn-lg btn-success dropdown-toggle" data-toggle="dropdown">
                        <i class="glyphicon glyphicon-plane"></i> Get started! <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="doc/current/get-started-gradle.html">Using Gradle</a></li>
                        <li><a href="doc/current/get-started-maven.html">Using Maven</a></li>
                        <li class="divider"></li>
                        <li><a href="doc/current/getting-started.html">What are Gradle and Maven?</a></li>
                      </ul>
                    </div>
                </p>
<#--
                <iframe width="420" height="315" src="//www.youtube.com/embed/rog8ou-ZepE" frameborder="0" allowfullscreen></iframe>
-->
                <iframe width="420" height="315" src="//www.youtube.com/embed/r2vd_KKWBek" frameborder="0" allowfullscreen></iframe>
                <p><strong>We need some screencast!</strong></p>
            </div>

			<div id="toc" class="toc2">
				<div id="toctitle" class="title">News</div>
				<ul class="sectlevel1">
                    <#list posts as post>
                        <#if (post.status == "published")>
                            <li><a href="${post.uri}"><#escape x as x?xml>${post.title}</#escape></a></li>
                        </#if>
                    </#list>
				</ul>
			</div>
		</div>
	</div>

<#--
	<div class="sect1">
		<h2 id="problems_we_solve"><a class="anchor" href="#problems_we_solve"></a>Problems we solve</h2>
		<div class="sectionbody">
			<div class="paragraph">
				<p></p>
			</div>
		</div>
	</div>

	<div class="sect1">
		<h2 id="high_level_overview"><a class="anchor" href="#high_level_overview"></a>High level overview</h2>
		<div class="sectionbody">
			<div class="paragraph">
				<p></p>
			</div>
		</div>
	</div>
-->

<#include "footer.ftl">