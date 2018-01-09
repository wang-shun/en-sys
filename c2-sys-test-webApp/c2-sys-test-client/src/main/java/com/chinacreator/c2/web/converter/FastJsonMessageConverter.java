package com.chinacreator.c2.web.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * User: Vurt
 * Date: 14-4-15
 * Time: 下午2:26
 */
public class FastJsonMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object>{


    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    // fastjson特性参数
    private SerializerFeature[] serializerFeature;

    public SerializerFeature[] getSerializerFeature() {
        return serializerFeature;
    }

    public void setSerializerFeature(SerializerFeature[] serializerFeature) {
        this.serializerFeature = serializerFeature;
    }

    public FastJsonMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }
    @Override
    protected boolean supports(Class<?> clazz) {
    	//TODO 先这样处理，要整理前台请求的accept和后台相应content-type的关系，避免JSON Converter处理类型为text/html text/plain等类型的响应
    	if(clazz.equals(String.class))
    		return false;
        return  true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = inputMessage.getBody().read()) != -1) {
            baos.write(i);
        }
        return JSON.parseObject(baos.toString("UTF-8"), clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        String jsonString = JSON.toJSONString(o, serializerFeature);
        OutputStream out = outputMessage.getBody();
        out.write(jsonString.getBytes(DEFAULT_CHARSET));
        out.flush();
    }

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return super.canRead(mediaType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object read(Type type, Class<?> contextClass,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i;
        while ((i = inputMessage.getBody().read()) != -1) {
            baos.write(i);
        }
		if (type instanceof Class && List.class.isAssignableFrom((Class)type)) {
		   return JSON.parseArray(baos.toString("UTF-8"), contextClass);
	   } else {
		   return JSON.parseObject(baos.toString("UTF-8"), type);
	   }
		
	}

}