import jenkins.model.Jenkins
import org.jenkinsci.plugins.github.config.GitHubPluginConfig
import org.jenkinsci.plugins.github.config.GitHubServerConfig
import org.jenkinsci.plugins.github.config.HookSecretConfig

def server = new GitHubServerConfig('GITHUB_TOKEN')
server.setName('ridakk')
server.setApiUrl('https://api.github.com')
server.setManageHooks(true)

def global_settings = Jenkins.instance.getExtensionList(GitHubPluginConfig.class)[0]

global_settings.configs = [server]
global_settings.overrideHookUrl = true
global_settings.hookUrl = new URL('https://my-jenkins/github-webhook/')
global_settings.hookSecretConfig = new HookSecretConfig('GITHUB_HOOK_SECRET')
global_settings.save()
