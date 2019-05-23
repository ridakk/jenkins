import jenkins.model.Jenkins
import hudson.security.SecurityRealm
import org.jenkinsci.plugins.GithubSecurityRealm

String githubWebUri = 'https://github.com'
String githubApiUri = 'https://api.github.com'
String oauthScopes = 'read:org,user:email'
String clientID = '<GITHUB-CLIENT-ID>'
String clientSecret = '<GITHUB-CLIENT-SECRET>'

SecurityRealm github_realm = new GithubSecurityRealm(githubWebUri, githubApiUri, clientID, clientSecret, oauthScopes)

Jenkins.instance.setSecurityRealm(github_realm)
Jenkins.instance.save()
println 'Security realm configuration has changed.  Configured GitHub security realm.'
