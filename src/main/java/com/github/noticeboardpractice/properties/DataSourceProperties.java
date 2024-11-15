package com.github.noticeboardpractice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperties {

    private String userName;
    private String password;
    private String driverClassName;
    private String url;
    private String secretKey;

}
