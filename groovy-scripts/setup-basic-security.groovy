import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)

hudsonRealm.createAccount("<username>","<password>")
instance.setSecurityRealm(hudsonRealm)
instance.save()
