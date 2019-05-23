import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.biouno.unochoice.model.GroovyScript
import org.biouno.unochoice.ChoiceParameter
import org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript
import org.biouno.unochoice.CascadeChoiceParameter
import hudson.model.ParametersDefinitionProperty

jenkins = Jenkins.instance

WorkflowJob job = jenkins.createProject(WorkflowJob, "WorkFlow-Job-With-Cascase-Choice-Parameter")
job.setDisplayName('WorkFlow Job With Cascase Choice Parameter')

def scriptContent = '''
  return ["Option 1", "Option 2"]
'''

GroovyScript script = new GroovyScript(new SecureGroovyScript(scriptContent, Boolean.TRUE), new SecureGroovyScript('', Boolean.TRUE))
ChoiceParameter param = new ChoiceParameter('PARAMETER', 'select paramter', script, CascadeChoiceParameter.PARAMETER_TYPE_SINGLE_SELECT, false)

def scriptContent2 = '''
  if (PARAMETER.equals("Option 1")) {
    return ["Cascade 1", "Cascade 2"]
  } else if (PARAMETER.equals("Option 2")) { 
    return ["Other 1", "Other 2"]
  } else {
    return ["Unknown parameter 1"]
  }
'''

GroovyScript script2 = new GroovyScript(new SecureGroovyScript(scriptContent2, Boolean.TRUE), new SecureGroovyScript('return ["-- Please select parameter 1 ---"]', Boolean.TRUE))
CascadeChoiceParameter param2 = new CascadeChoiceParameter('PARAMETER 2', 'cascaded parameter', script2, CascadeChoiceParameter.PARAMETER_TYPE_SINGLE_SELECT, 'PARAMETER', true)

job.addProperty(new ParametersDefinitionProperty([param, param2]))
