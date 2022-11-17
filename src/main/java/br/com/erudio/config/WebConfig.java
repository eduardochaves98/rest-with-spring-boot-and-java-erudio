package br.com.erudio.config;

import br.com.erudio.serializationConverter.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var AllowedOrigins = corsOriginPatterns.trim().split(",");
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(AllowedOrigins)
                .allowCredentials(true);
//              .allowedMethods("GET", "POST", "PUT")

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //Via Extention Deprecated since SB 2.6
//        //Via Query param. "?mediaType=xml"
//        configurer.favorParameter(true)
//                .parameterName("mediaType")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json",MediaType.APPLICATION_JSON)
//                .mediaType("xml",MediaType.APPLICATION_XML);
//    }
        //Via HEADER param. "?mediaType=xml"
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json",MediaType.APPLICATION_JSON)
                    .mediaType("xml",MediaType.APPLICATION_XML)
                    .mediaType("x-yaml",MEDIA_TYPE_APPLICATION_YML);
    }
}
