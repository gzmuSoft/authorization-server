/**
 * 新增的 sms 授权方式
 * 由于 spring security oauth2 并没有提供基于短信的认证方式，我们必须自己实现。
 * <p>
 * 对于所有的请求，我们只会拦截 <code>/oauth/sms</code> 请求
 * 通过 {@link cn.edu.gzmu.authserver.auth.sms.SmsAuthenticationFilter} 过滤器进行拦截
 * 获取到需要的参数后，将他封装为我们自己的 {@link org.springframework.security.authentication.AbstractAuthenticationToken}
 * 实现类 {@link cn.edu.gzmu.authserver.auth.sms.SmsAuthenticationToken}
 * 将其交给 {@link org.springframework.security.authentication.AuthenticationManager}
 * 他将会检索所有已经实现 {@link org.springframework.security.authentication.AuthenticationProvider} 的子类
 * <p>
 * 我们需要提供一个他的子类 {@link cn.edu.gzmu.authserver.auth.sms.SmsAuthenticationProvider}
 * 并实现接口中所有的方法，使用它来校验授权与用户信息。
 * 依然会去请求数据库，我通过实现自己写的 {@link cn.edu.gzmu.authserver.auth.sms.SmsUserDetailsService} 接口
 * 让 {@link cn.edu.gzmu.authserver.service.impl.UserDetailsServiceImpl} 实现其所有方法
 * 通过它就可以获取到用户信息。
 * <p>
 * 最后我们需要将他交给登录成功处理器 {@link cn.edu.gzmu.authserver.auth.sms.SmsSuccessHandler}
 * 进行构建完整的 token 信息
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-20 15:27
 * @date 19-7-31 10:12
 * @deprecated 过于复杂的配置方式，标记过时
 * 参见 <a href="https://echocow.cn/articles/2019/07/30/1564498598952.html">Spring Security Oauth2 从零到一完整实践（五） 自定义授权模式（手机、邮箱等） </a>
 */
package cn.edu.gzmu.authserver.auth.sms;