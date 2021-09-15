package wang.lonelymoon.scaffold.common.config.excel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * @author wangpeng
 * @description
 * @date 2021/9/15
 **/
@Configuration
public class ExcelConfig {
    @Bean
    public BeanNameViewResolver getBeanNameViewResolver() {
        BeanNameViewResolver view = new BeanNameViewResolver();
        view.setOrder(-100);
        return view;
    }
}
