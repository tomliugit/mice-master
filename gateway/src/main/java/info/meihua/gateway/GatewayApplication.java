package info.meihua.gateway;

import info.meihua.gateway.filter.TokenFilter;
import info.meihua.gateway.filter.XSSFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author sunwell
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public TokenFilter preRequestFilter() {
        return new TokenFilter();
    }


    @Bean
    public XSSFilter xssRequestFilter() {
        return new XSSFilter();
    }
}
