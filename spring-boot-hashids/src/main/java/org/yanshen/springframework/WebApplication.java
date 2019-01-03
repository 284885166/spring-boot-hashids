package org.yanshen.springframework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.yanshen.springframework.formatter.HashidsFormat;
import org.yanshen.springframework.formatter.HashidsFormatterFactory;
import org.yanshen.springframework.utils.AnnotationUtils;
import org.yanshen.springframework.utils.HashidsUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 启动类，自带hashid与id的转换配置，并且使用了FastJson作为Json的序列化工具
 *
 * @author yanshen
 * Created by yanshen on 2019/1/3.
 */
public class WebApplication extends WebMvcConfigurationSupport {

    /**
     * 添加普通输入参数的hashid转换，如@RequestParam, @PathVariable注解的参数
     *
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        HashidsFormatterFactory hashidsFormatterFactory = new HashidsFormatterFactory();
        registry.addFormatterForFieldAnnotation(hashidsFormatterFactory);
    }

    /**
     * 添加json输入和输出的hashid转换器，并且依赖使用FastJson，如@RequestBody注解的参数和返回json格式的数据
     *
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter convert = fastJsonConfigure(getIntegerCodec(), getSerializeFilter());
        converters.add(convert);
    }

    /**
     * json输入参数转换器
     *
     * @return
     */
    protected IntegerCodec getIntegerCodec() {
        IntegerCodec integerCodec = new IntegerCodec() {
            @Override
            public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
                // 获取需要转换的类的实例
                Object contentObject = parser.getContext().object;
                // 判断字段属性是否包含HashidsFormat注解
                if (contentObject != null && AnnotationUtils.fieldHasAnnotation(contentObject.getClass(), ((String) fieldName), HashidsFormat.class)) {
                    JSONLexer lexer = parser.lexer;
                    int token = lexer.token();
                    if (token == JSONToken.NULL) {
                        lexer.nextToken(JSONToken.COMMA);
                        return null;
                    }
                    // 字符串Id转换为整型
                    if (token == JSONToken.LITERAL_STRING) {
                        String value = (String) parser.parse();
                        if (!value.matches("\\d+")) {
                            Integer intObj = HashidsUtils.decode(value);
                            return (T) intObj;
                        }
                    }
                }
                return super.deserialze(parser, clazz, fieldName);
            }
        };
        return integerCodec;
    }

    /**
     * json输出转换器
     *
     * @return
     */
    protected ValueFilter getSerializeFilter() {
        ValueFilter filter = (object, name, value) -> {
            // 判断字段属性是否包含HashidsFormat注解
            if (object != null && AnnotationUtils.fieldHasAnnotation(object.getClass(), name, HashidsFormat.class)) {
                return HashidsUtils.encode((Integer) value);
            }
            return value;
        };
        return filter;
    }

    /**
     * 配置FastJson转换器
     *
     * @param integerCodec
     * @param filter
     * @return
     */
    public FastJsonHttpMessageConverter fastJsonConfigure(IntegerCodec integerCodec, ValueFilter filter) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter() {
            @Override
            public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                try {
                    InputStream in = inputMessage.getBody();
                    byte[] bytes = IOUtils.toByteArray(in);
                    Charset charset = getFastJsonConfig().getCharset();
                    if (charset == null) {
                        charset = com.alibaba.fastjson.util.IOUtils.UTF8;
                    }
                    String json = new String(bytes, charset);
                    // 使用自定义的ParserConfig类
                    return JSON.parseObject(json, type, getFastJsonConfig().getParserConfig(), JSON.DEFAULT_PARSER_FEATURE, getFastJsonConfig().getFeatures());
                } catch (JSONException ex) {
                    throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
                } catch (IOException ex) {
                    throw new HttpMessageNotReadableException("I/O error while reading input message", ex);
                }
            }
        };
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /*fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);*/
        // 设置序列化，id => hash id
        fastJsonConfig.setSerializeFilters(filter);
        // 设置反序列化 has id => id
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.putDeserializer(int.class, integerCodec);
        parserConfig.putDeserializer(Integer.class, integerCodec);
        fastJsonConfig.setParserConfig(parserConfig);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
