import jenkins.model.*
import hudson.model.*
import jenkins.plugins.nodejs.tools.*
import hudson.tools.*

def inst = Jenkins.getInstance()

def desc = inst.getDescriptor("jenkins.plugins.nodejs.tools.NodeJSInstallation")

def versions = [
  "Node.js 8.11.2": "8.11.2",
  "Node.js 8.11.4": "8.11.4",
  "Node.js 8.12.0": "8.12.0",
]
def installations = []

for (v in versions) {
  def installer = new NodeJSInstaller(v.value, "", 100)
  def installerProps = new InstallSourceProperty([installer])
  def installation = new NodeJSInstallation(v.key, "", [installerProps])
  installations.push(installation)
}

desc.setInstallations(installations.toArray(new NodeJSInstallation[0]))

desc.save() 
