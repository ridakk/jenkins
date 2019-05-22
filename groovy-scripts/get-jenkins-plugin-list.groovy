def plugins = jenkins.model.Jenkins.instance.getPluginManager().getPlugins();
def list = new ArrayList(plugins).sort{it.getShortName().toLowerCase()};

list.each {println "${it.getShortName()}: ${it.getVersion()}"}
