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

Set<Permission> project1Permissions = new HashSet<Permission>()
project1Permissions.add(Permission.fromId("hudson.model.Item.Read"))
project1Permissions.add(Permission.fromId("hudson.model.Item.Build"))
project1Permissions.add(Permission.fromId("hudson.model.Item.Workspace"))
project1Permissions.add(Permission.fromId("hudson.model.Item.Cancel"))
Role project1Role = new Role("Project1", "^Project1/.*", project1Permissions)
rbac.addRole(RoleBasedAuthorizationStrategy.PROJECT, project1Role)

rbac.assignRole(RoleBasedAuthorizationStrategy.PROJECT, project1Role, 'user1')

Set<Permission> project2Permissions = new HashSet<Permission>()
project2Permissions.add(Permission.fromId("hudson.model.Item.Read"))
project2Permissions.add(Permission.fromId("hudson.model.Item.Build"))
project2Permissions.add(Permission.fromId("hudson.model.Item.Workspace"))
project2Permissions.add(Permission.fromId("hudson.model.Item.Cancel"))
project2Permissions.add(Permission.fromId("hudson.model.View.Configure"))
Role project2Role = new Role("Project2", "^Project2/.*", project2Permissions)
rbac.addRole(RoleBasedAuthorizationStrategy.PROJECT, project2Role)

rbac.assignRole(RoleBasedAuthorizationStrategy.PROJECT, project2Role, 'user2')

Jenkins.instance.save()
