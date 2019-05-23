import jenkins.model.Jenkins

Jenkins.instance.getDescriptor("jenkins.CLI").get().setEnabled(false)
