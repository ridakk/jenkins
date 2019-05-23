import static jenkins.model.Jenkins.instance as jenkins
import jenkins.branch.*
import org.jenkinsci.plugins.github_branch_source.*
import org.jenkinsci.plugins.githubScmTraitNotificationContext.NotificationContextTrait
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProjectFactory
import com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy
import jenkins.plugins.git.traits.WipeWorkspaceTrait

// Create the top-level item if it doesn't exist already.
def org = jenkins.createProject(OrganizationFolder, 'Organization-Folder-Pull-Request-Builder')
org.setDisplayName('Organization Folder Pull Request Builder')

// Set up GitHub source.
def navigator = new GitHubSCMNavigator('ridakk')
navigator.apiUri = 'https://api.github.com'
navigator.credentialsId = 'GITHUB_USER'
navigator.traits = [
  new jenkins.scm.impl.trait.WildcardSCMSourceFilterTrait('jenkins', ''),
  new OriginPullRequestDiscoveryTrait(1), // Merging the pull request with the current target branch revision.
  new NotificationContextTrait('continuous-integration/jenkins/pull_request', true),
  new WipeWorkspaceTrait()
]
org.navigators.replace(navigator)

// Delete orphan items after 1 days
org.orphanedItemStrategy = new DefaultOrphanedItemStrategy(true, "1", "")

// Configure what Jenkinsfile we should be looking for
WorkflowMultiBranchProjectFactory factory = new WorkflowMultiBranchProjectFactory()
factory.scriptPath = 'pipelines/Organization-Folder-Pull-Request-Builder/Jenkinsfile'
org.projectFactories.replace(factory)

// configure the repos to automatically build
org.addProperty(new NoTriggerOrganizationFolderProperty('.*'))

jenkins.save()

Thread.start {
    sleep 3000 // 3 seconds
    println '----> Scan for job'
    org.scheduleBuild()
}
