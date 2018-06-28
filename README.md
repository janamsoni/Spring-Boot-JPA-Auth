# Spring-Boot-JPA-Auth
Login Example With Spring Security JPA Authantication

Spring Security JPA Auth

Must Have Dependencies
Spring-boot-starter-data-jpa
Spring-boot-starter-security
Tomcat-embed-jasper
Jstl
---------------------------------------------------------------------------------------------------------------------
First Define JPA Entities (Classes)
User (VO)
UserRole (VO)
Define JPA Repositories (Interfaces)
UserRepository
Like
public interface UserRepository extends JpaRepository<User, Long> 
UserRoleRepository
Like
public interface RoleRepository extends JpaRepository<Role, Long>

Define UserDetailsService (Service Class)
To implement login/authentication with Spring Security, we need to implement org.springframework.security.core.userdetails.UserDetailsService interface So Implement this UserDetailsService Interface in Your Service Class
Like Below
@Service
public class UserDetailsServiceImpl implements UserDetailsService
Now Autowire Your Repository Like 
@Autowired
private UserRepository userRepository;
Now Override loadUserByUsername  Method in this class.and call your findByUsername(username) method from userRepository in loadUserByUsername Method.
It will return User’s Object so that You can get Username ,Password and its Roles.
Create SET For User’s Roles 
Like Below
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
Get Users Roles From Repository and Assign to this SET grantedAuthorities
Now You Have user Object As Well as grantedAuthorities So Return 
return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),grantedAuthorities);
Because loadUserByUsername  Method has Return Type UserDetails.
Now Relax.
But Wait………..
How Spring Will Know that you have implemented UserDetailsService ?
For that you have to configure one new class which tells spring about your security configuration which you have defined.
So create new Class name it anything like WebSecurityConfig 
But you must have to extends WebSecurityConfigurerAdapter
Like
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
Now Autowire UserDetailsService Like Below
@Autowired 
 private UserDetailsService userDetailsService;
Create Password Encoder Bean because Spring Recommend to Use BCryptPasswordEncoder to store password in Hash Form Like
$2a$10$BCpa5XapuYl2DiJM5z2Bj.5w64ldkR.JS3IJy.gUhZbMMFfmtqiwO
 Rather than Plain text Password. janam
So create passwordEncoder Bean Like below
@Bean(name="passwordEncoder")
public PasswordEncoder passwordencoder()
{
     return new BCryptPasswordEncoder();
}

Now Override 2 Methods to Configure Authentication and Authorization.
protected void configure(AuthenticationManagerBuilder auth)
 protected void configure(HttpSecurity http) 




In protected void configure(AuthenticationManagerBuilder auth) provide authentication with UserDetailsService. It will be used to authentication of the user. Spring Will Automatically Do rest of your work.
Like Below
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception 
{
 auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
}

At last Finally Configure HttpSecurity in 
 protected void configure(HttpSecurity http) method
Like Below
@Override
 protected void configure(HttpSecurity http) throws Exception {
   http.authorizeRequests()
  .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
  .anyRequest().permitAll()
  .and()
    .formLogin().loginPage("/login")
    .usernameParameter("username").passwordParameter("password")
  .and()
    .logout().logoutSuccessUrl("/login?logout") 
   .and()
   .exceptionHandling().accessDeniedPage("/403")
  .and()
    .csrf();
 }

Here 
   http.authorizeRequests()
  .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
  .anyRequest().permitAll()
Which Means Users having Role of 'ROLE_ADMIN' IN UserRole Table can Access /hello Page.


Here
.formLogin().loginPage("/login")
.usernameParameter("username").passwordParameter("password")
Which Means Our Custom Login Page URL is /login and its username and password parameters are username and password
So Our Login Page Should be Like
<form action="/login" method="post">

		<div class="lc-block">
			<div>
				<input type="text" class="style-4" name="username"
					placeholder="User Name" />
			</div>
			<div>
				<input type="password" class="style-4" name="password"
					placeholder="Password" />
			</div>
			<div>
				<input type="submit" value="Sign In" class="button red small" />
			</div>
					
</div>
</form>


You can Specify Deiffert URL Pattern for different User’s Roles Like below.
.antMatchers("/users/**").hasRole("USER")//USER role can access /users/**
 .antMatchers("/admin/**").hasRole("ADMIN")//ADMIN role can access /admin/**
 .antMatchers("/quests/**").permitAll()// anyone can access /quests/**








