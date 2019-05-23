import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.biouno.unochoice.model.GroovyScript
import org.biouno.unochoice.ChoiceParameter
import org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript
import org.biouno.unochoice.CascadeChoiceParameter
import hudson.model.ParametersDefinitionProperty

jenkins = Jenkins.instance

WorkflowJob job = jenkins.createProject(WorkflowJob, "WorkFlow-Job-With-Choice-Parameter")
job.setDisplayName('WorkFlow Job With Choice Parameter')

def scriptContent = '''
  return ["Option 1", "Option 2"]
'''

GroovyScript script = new GroovyScript(new SecureGroovyScript(scriptContent, Boolean.TRUE), new SecureGroovyScript('', Boolean.TRUE))
ChoiceParameter param = new ChoiceParameter('PARAMETER', 'select paramter', script, CascadeChoiceParameter.PARAMETER_TYPE_SINGLE_SELECT, false)

job.addProperty(new ParametersDefinitionProperty([param]))
