import jenkins.model.Jenkins
import jenkins.security.s2m.AdminWhitelistRule

def j = Jenkins.instance

j.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)
