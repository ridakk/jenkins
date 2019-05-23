import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

jenkins = Jenkins.instance

WorkflowJob job = jenkins.createProject(WorkflowJob,  "WorkFlow-Job-With-Jenkinsfile-From-File")
job.setDisplayName('WorkFlow Job With Jenkinsfile From File')

String fileContents = new File('/var/jenkins_home/pipelines/WorkFlow-Job-With-Jenkinsfile-From-File/Jenkinsfile').getText('UTF-8')
job.definition = new CpsFlowDefinition(fileContents, Boolean.TRUE)
