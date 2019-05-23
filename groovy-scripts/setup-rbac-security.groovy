import jenkins.model.Jenkins
import com.michelin.cio.hudson.plugins.rolestrategy.*

// Set new authentication strategy
RoleBasedAuthorizationStrategy roleBasedAuthenticationStrategy = new RoleBasedAuthorizationStrategy()

Jenkins.instance.setAuthorizationStrategy(roleBasedAuthenticationStrategy)
