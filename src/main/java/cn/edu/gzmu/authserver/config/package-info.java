/**
 * 项目所有的配置信息都在这里.
 *
 * {@link cn.edu.gzmu.authserver.config.ApplicationConfig}
 * 应用的基本配置，包括 redis 工厂、rest 接口
 * {@link cn.edu.gzmu.authserver.config.AuthTokenStore}
 * 授权的 token 存储配置, 包括 redis 存储令牌，postgres 存储客户端信息
 * 以及 jwt 令牌和 jwk 的一些配置
 * {@link cn.edu.gzmu.authserver.config.Oauth2AuthorizationServerConfig}
 * 授权服务器的配置
 * {@link cn.edu.gzmu.authserver.config.Oauth2ResourceServerConfig}
 * 资源服务器的配置
 *
 * 授权中心即是授权服务器，又是资源服务器
 * 但是他只对外提供查询的操作，不提供修改操作
 * 修改操作需要作为另外一套资源服务器来进行控制
 *
 * {@link cn.edu.gzmu.authserver.config.WebSecurityConfig}
 * 安全相关的配置，此处的优先级高于 资源服务器 的优先级
 * 所以他会完全覆盖掉 资源服务器 中的额皮质
 * 为了统一，我们所有的权限都放在这里进行配置
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/3 上午10:36
 */
package cn.edu.gzmu.authserver.config;