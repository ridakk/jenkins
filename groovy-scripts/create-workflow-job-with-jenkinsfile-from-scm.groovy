import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

jenkins = Jenkins.instance

WorkflowJob job = jenkins.createProject(WorkflowJob,  "WorkFlow-Job-With-Jenkinsfile-From-SCM")
job.setDisplayName('WorkFlow Job With Jenkinsfile From SCM')

GitSCM scm = new GitSCM("https://github.com/ridakk/jenkins.git")
scm.branches = [new BranchSpec("*/master")]

CpsScmFlowDefinition definition = new CpsScmFlowDefinition(scm, "/pipelines/WorkFlow-Job-With-Jenkinsfile-From-SCM/Jenkinsfile")
definition.scm.userRemoteConfigs[0].credentialsId = 'GITHUB_USER'
definition.setLightweight(true)

job.definition = definition
