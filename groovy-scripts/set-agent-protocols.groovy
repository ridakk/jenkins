import jenkins.model.Jenkins

def j = Jenkins.instance
Set<String> agentProtocolsList = ['JNLP4-connect', 'Ping']
if(!j.getAgentProtocols().equals(agentProtocolsList)) {
    j.setAgentProtocols(agentProtocolsList)
    println("Agent Protocols have changed.  Setting: ${agentProtocolsList}")
    j.save()
}
