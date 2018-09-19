package cn.jerio.manager.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义http消息转换器
 * Created by Jerio on 2018/09/19
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class ManagerConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter httpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //在convert中添加配置信息.
        httpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        httpMessageConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverter<?> converter = httpMessageConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * 自定义拦截配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/*.html","/css/**", "/js/**", "/fonts/**","/plugins/**", "/img/**","/login","/error").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/*").hasRole("ADMIN") // 需要相应的角色才能访问
        .and().formLogin()   //基于 Form 表单登录验证
                .loginPage("/login")// 自定义登录界面
                .defaultSuccessUrl("/admin/index",true)
                .failureUrl("/error.html")
        .and().logout()
        .and().headers().frameOptions().sameOrigin();
    }

    /**
     * 认证信息管理
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        auth.authenticationProvider(authenticationProvider());
        auth.inMemoryAuthentication().withUser("houjie").password("{noop}jerio").roles("ADMIN");
    }
}
