import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.biouno.unochoice.model.GroovyScript
import org.biouno.unochoice.ChoiceParameter
import org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript
import org.biouno.unochoice.CascadeChoiceParameter
import hudson.model.ParametersDefinitionProperty

jenkins = Jenkins.instance

WorkflowJob job = jenkins.createProject(WorkflowJob, "WorkFlow-Job-With-Dynamic-Choice-Parameter")
job.setDisplayName('WorkFlow Job With Dynamic Choice Parameter')

def scriptContent = '''
  import groovy.json.JsonSlurper

  def URL = "https://api.github.com/repos/ridakk/jenkins/contents"

  def conn = URL .toURL().openConnection()

  def names = []
  if( conn.responseCode == 200 ) {
     def files = new JsonSlurper().parseText(conn.content.text)

    for (file in files) {
       names.push(file.name)
    }

    return names
  } else {
    return ["failed"]
  }
'''

GroovyScript script = new GroovyScript(new SecureGroovyScript(scriptContent, Boolean.TRUE), new SecureGroovyScript('failed to load options', Boolean.TRUE))
ChoiceParameter param = new ChoiceParameter('PARAMETER', 'select paramter', script, CascadeChoiceParameter.PARAMETER_TYPE_SINGLE_SELECT, false)

job.addProperty(new ParametersDefinitionProperty([param]))
