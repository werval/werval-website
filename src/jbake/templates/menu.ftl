	<!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>index.html">QiWeb</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>news.html">News</a></li>
            <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>doc/index.html">Documentation</a></li>
            <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>resources/skeletons.html">Skeletons</a></li>
            <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>resources/samples.html">Samples</a></li>

            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">More... <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>community.html">Community</a></li>
                <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>faq.html">F.A.Q</a></li>
                <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>security.html">Security</a></li>
                <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>downloads.html">Downloads</a></li>
              </ul>
            </li>

          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <div class="container">
