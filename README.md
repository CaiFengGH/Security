>Security

基于SpringSecurity+SpringSocial+OAuth2实现表单登录、短信验证码登录和QQ第三方登陆

>SpringMvc开发RESTFul API常用注解

(1)@RequestParam(name = "username",required = false,defaultValue = "caifeng")

(2)UserQueryCondition:将多种参数组合为查询对象

(3)@PageableDefault(size=10,page=1,sort="username,asc")

(4)@PathVariable:映射url片段到java方法参数

(5)在url中写正则表达式

(6)@JsonView:控制json的内容输出

(7)@RequestBody:映射请求体到java方法的参数

(8)日期类型的处理

(9)@Valid和BindingResult:hibernate.validator

(10)自定义消息@NotBlank(message="不能为空")

(11)自定义校验注解

(12)RESTful错误处理:

Spring Boot中默认的错误异常处理机制(自动判断浏览器请求:html app请求:json)

@ControllerAdvice:自定义异常处理

(13)RESTful API的拦截

执行顺序:过滤器 -> 拦截器 -> 切片

过滤器:可以获得原始的Http请求和响应，

拦截器:可以获得原始的Http请求和响应，并且包括调用的方法，但是拿不到参数

Aspect:可以获得调用方法的参数，但是拿不到原始的Http请求和响应


>SpringSecurity学习

(1)认证 + 授权 + 防止伪造攻击

(2)Spring过滤器链

默认配置:http响应表单，采用user和后台生成默认密码

基本原理:

FilterSecurityInterceptor + ExceptionTranslationFilter + UsernamePasswordAuthenticationFilter

(3)自定义登录逻辑

UserDetailsService: loadUserByUsername

UserDetail: 根据数据库信息对用户进行判断

passwordEncoder: 用于对用户密码进行加密和解密

(4)个性化认证流程

loginPage: 指定表单登录时的指定页面

loginProcessingUrl: 指定UsernamePasswordAuthenticationFilter处理这个请求

SecurityProperties: 所有配置项内容

SecurityCoreConfig: 使所有的配置项生效

AuthenticationSuccessHandler: 登录成功处理器

AuthenticationFailureHandler: 登录失败处理器

为支持模板语言的重定向，需要判断是json方式还是redirect方式

(5)图片验证码

ValidateCodeFilter extends OncePerRequestFilter(加载一次) implements InitializingBean

ValidateCodeException extends AuthenticationException

请求级配置 > 应用级配置 > 默认配置 

(6)记住我

RememberMeAuthenticationFilter

PersistentTokenRepository: 储存token字段

(7)手机短信验证码开发

结合Spring提供的表单登录流程，开发自定义的短信登录

>QQ第三方开发

(1)OAuth2协议(资源所有者A + 服务提供商B + 第三方应用C)

第三方应用将用户导向服务提供商->用户同意授权->返回第三方应用并携带授权码

->第三方应用向认证服务器申请令牌->认证服务器向第三方应用发送令牌->获取用户信息

->根据用户信息构建Authentication并放入SecurityContext中

(2)SpringSocail开发

a-ServiceProvider包括OAuth2Operation和API，而ServiceProvider由AbstractOAuth2ServiceProvider实现，

OAuth2Operations由OAuth2Template实现，API由AbstractOAuth2ApiBinding实现

b-ConnectionFactory由ServiceProvider和ApiAdapter组成，ConnectionFactory由OAuth2ConnectionFactory

实现
