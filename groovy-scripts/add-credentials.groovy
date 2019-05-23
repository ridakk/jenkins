import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import com.cloudbees.jenkins.plugins.awscredentials.AWSCredentialsImpl
import hudson.util.Secret
import jenkins.model.*
import hudson.security.*
domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()


GITHUB_TOKEN = new StringCredentialsImpl(CredentialsScope.GLOBAL,"GITHUB_TOKEN","GITHUB_TOKEN",Secret.fromString("<TOKEN>"))
GITHUB_USER = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,"GITHUB_USER", "GITHUB_USER","<USER>","<PASSWORD>")
AWS_CREDENTIAL = new AWSCredentialsImpl(CredentialsScope.GLOBAL,"AWS_CREDENTIAL", "<AWS-KEY>","<AWS-SECRET>","Aws Credential")

store.addCredentials(domain, GITHUB_TOKEN)
store.addCredentials(domain, GITHUB_USER)
store.addCredentials(domain, AWS_S3_CREDENTIAL)
