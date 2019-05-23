import hudson.model.*
import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.TriggersConfig
import hudson.tools.*

def sonar_name = "sonarqube"
def sonar_server_url = "<sonarqube-server-url>"
def sonar_auth_token = "<sonarqube-auth-token>"
def sonar_mojo_version = ''
def sonar_additional_properties = ''
def sonar_triggers = new TriggersConfig()
def sonar_additional_analysis_properties = ''
def sonar_runner_name = 'SonarQube Scanner '
def sonar_runner_version = '2.8'

def instance = Jenkins.getInstance()

Thread.start {

    sleep(1000)
    println("Configuring SonarQube...")

    // Get the GlobalConfiguration descriptor of SonarQube plugin.
    def SonarGlobalConfiguration sonar_conf = instance.getDescriptor(SonarGlobalConfiguration.class)

    def sonar_inst = new SonarInstallation(
        sonar_name,
        sonar_server_url,
        sonar_auth_token,
        sonar_mojo_version,
        sonar_additional_properties,
        sonar_triggers,
        sonar_additional_analysis_properties
    )

    // Only add the new Sonar setting if it does not exist - do not overwrite existing config
    def sonar_installations = sonar_conf.getInstallations()
    def sonar_inst_exists = false
    sonar_installations.each {
        installation = (SonarInstallation) it
        if (sonar_inst.getName() == installation.getName()) {
            sonar_inst_exists = true
            println("Found existing installation: " + installation.getName())
        }
    }
    if (!sonar_inst_exists) {
        sonar_installations += sonar_inst
        sonar_conf.setInstallations((SonarInstallation[]) sonar_installations)
        sonar_conf.save()
    }

    // Step 2 - Configure SonarRunner
    println("Configuring SonarRunner...")
    def desc_SonarRunnerInst = instance.getDescriptor("hudson.plugins.sonar.SonarRunnerInstallation")
    def sonarRunnerInstaller = new SonarRunnerInstaller(sonar_runner_version)
    def installSourceProperty = new InstallSourceProperty([sonarRunnerInstaller])
    def sonarRunner_inst = new SonarRunnerInstallation(sonar_runner_name + sonar_runner_version, "", [installSourceProperty])

    // Only add our Sonar Runner if it does not exist - do not overwrite existing config
    def sonar_runner_installations = desc_SonarRunnerInst.getInstallations()
    def sonar_runner_inst_exists = false
    sonar_runner_installations.each {
        installation = (SonarRunnerInstallation) it
        if (sonarRunner_inst.getName() == installation.getName()) {
            sonar_runner_inst_exists = true
            println("Found existing installation: " + installation.getName())
        }
    }
    if (!sonar_runner_inst_exists) {
        sonar_runner_installations += sonarRunner_inst
        desc_SonarRunnerInst.setInstallations((SonarRunnerInstallation[]) sonar_runner_installations)
        desc_SonarRunnerInst.save()
    }

    // Save the state
    instance.save()
}
