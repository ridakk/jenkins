import hudson.*
import hudson.model.*
import hudson.security.*
import jenkins.*
import jenkins.model.*
import java.util.*
import com.michelin.cio.hudson.plugins.rolestrategy.*
import java.lang.reflect.*

def rbac = Hudson.instance.getAuthorizationStrategy()

Constructor[] constrs = Role.class.getConstructors()
for (Constructor<?> c : constrs) {
  c.setAccessible(true)
}

// Make the method assignRole accessible
Method assignRoleMethod = RoleBasedAuthorizationStrategy.class.getDeclaredMethod("assignRole", String.class, Role.class, String.class)
assignRoleMethod.setAccessible(true)

// Create admin set of permissions
Set<Permission> adminPermissions = new HashSet<Permission>()
adminPermissions.add(Permission.fromId("hudson.model.Hudson.Administer"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.FullControl"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericRead"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericWrite"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericCreate"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericUpdate"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericDelete"))
adminPermissions.add(Permission.fromId("hudson.security.Permission.GenericConfigure"))
adminPermissions.add(Permission.fromId("hudson.model.Hudson.Read"))
adminPermissions.add(Permission.fromId("hudson.model.Hudson.RunScripts"))
adminPermissions.add(Permission.fromId("hudson.model.Hudson.UploadPlugins"))
adminPermissions.add(Permission.fromId("hudson.model.Hudson.ConfigureUpdateCenter"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Configure"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.ExtendedRead"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Delete"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Create"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Disconnect"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Connect"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Build"))
adminPermissions.add(Permission.fromId("hudson.model.Computer.Provision"))
adminPermissions.add(Permission.fromId("hudson.model.Run.Delete"))
adminPermissions.add(Permission.fromId("hudson.model.Run.Update"))
adminPermissions.add(Permission.fromId("hudson.model.Run.Artifacts"))
adminPermissions.add(Permission.fromId("hudson.model.Run.Replay"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Create"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Delete"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Configure"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Read"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Discover"))
adminPermissions.add(Permission.fromId("hudson.model.Item.ExtendedRead"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Build"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Workspace"))
adminPermissions.add(Permission.fromId("hudson.model.Item.WipeOut"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Cancel"))
adminPermissions.add(Permission.fromId("hudson.model.Item.Move"))
adminPermissions.add(Permission.fromId("hudson.scm.SCM.Tag"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.UseOwn"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.UseItem"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.Create"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.Update"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.View"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.Delete"))
adminPermissions.add(Permission.fromId("com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains"))
adminPermissions.add(Permission.fromId("hudson.model.View.Create"))
adminPermissions.add(Permission.fromId("hudson.model.View.Delete"))
adminPermissions.add(Permission.fromId("hudson.model.View.Configure"))
adminPermissions.add(Permission.fromId("hudson.model.View.Read"))

// Create the admin Role
Role adminRole = new Role("admin", adminPermissions)
rbac.addRole(RoleBasedAuthorizationStrategy.GLOBAL, adminRole)

/// Read access for authenticated users
// Create permissions
Set<Permission> authenticatedPermissions = new HashSet<Permission>()
authenticatedPermissions.add(Permission.fromId("hudson.security.Permission.GenericRead"))
authenticatedPermissions.add(Permission.fromId("hudson.model.Hudson.Read"))
authenticatedPermissions.add(Permission.fromId("hudson.model.Item.Read"))
authenticatedPermissions.add(Permission.fromId("hudson.model.View.Read"))

// Create the readonly Role
Role readonlyRole = new Role("readonly", authenticatedPermissions)
rbac.addRole(RoleBasedAuthorizationStrategy.GLOBAL, readonlyRole)

rbac.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, adminRole, 'admin1')
rbac.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, adminRole, 'admin2')

rbac.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, readonlyRole, 'user1')
rbac.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, readonlyRole, 'user2')

Jenkins.instance.save()
