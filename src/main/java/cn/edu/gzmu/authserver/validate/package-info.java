/**
 * 提供验证码类型的所有服务，提供一套完整的发送、验证服务。
 * <p>
 * 获取验证码：
 * 对于 sms 服务，将会自动匹配 /code/sms 请求，通过 {@link cn.edu.gzmu.authserver.validate.ValidateCodeProcessorHolder}
 * 获取对应的验证码处理器，处理其继承于 {@link cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeProcessor}
 * 提供了一套默认完整的生成、保存操作，具体查看其注释。
 * 遵循单一职责原则，每个接口只提供一个方法，因此对于每个验证码应该分别有一下接口的实现类：
 * <p>
 * {@link cn.edu.gzmu.authserver.validate.ValidateCodeSender} 验证码发送方式
 * 对于不同的验证方式，发送验证码的方式也是不同的。
 * <p>
 * {@link cn.edu.gzmu.authserver.validate.ValidateCodeGenerator} 验证码的生成规则
 * 对于不同的验证方式，验证码的生成规则也是不同的。
 * <p>
 * {@link cn.edu.gzmu.authserver.validate.ValidateCodeRepository} 在 redis 中，验证码存储的键
 * 对于不同的验证方式，验证码存储的键应该有不同的键。可以通过继承抽象类 {@link cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeRepository}
 * 其提供了一套默认的处理方式，但是你必须实现 <code>buildKey</code> 方法进行自定义键生成策略。
 * <p>
 * {@link cn.edu.gzmu.authserver.validate.ValidateCodeProcessor} 验证码处理器
 * 此方法由 抽象类 {@link cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeProcessor} 实现，不同的验证码
 * 的行为可以通过继承此类进行修改
 * <p>
 * 验证验证码：
 * 默认的验证方式由抽象类 {@link cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeProcessor} 进行维护
 * 默认情况下主要通过 {@link cn.edu.gzmu.authserver.model.constant.ValidateCodeType} 的枚举作为键来获取请求体中对应的验证码
 * 验证方法为 <code>validate</code> ，子类可以自由覆盖并修改验证规则。
 *
 * 现在一有两种验证码实现，参见 {@link cn.edu.gzmu.authserver.validate.email} 和  {@link cn.edu.gzmu.authserver.validate.sms}
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-20 15:05
 * @date 19-5-22 11:53
 * @date 19-7-31 10:54 标记过于复杂的实现为过时状态
 */
package cn.edu.gzmu.authserver.validate;