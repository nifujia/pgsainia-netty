package com.pgsainia.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class SerializationUtil {

    public SerializationUtil() {

    }

    private static final Map<Class<?>, Schema<?>> cacheSchemaMap = new ConcurrentHashMap<>();
    private static Objenesis objenesis = new ObjenesisStd();

    /**
     * 反序列化，字节数组->对象
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            T message = objenesis.newInstance(clazz);
            Schema<T> schema = getSchema(clazz);
            ProtobufIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    /**
     * 序列化，对象->字节数组
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtobufIOUtil.toByteArray(object, schema, linkedBuffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            linkedBuffer.clear();
        }

    }

    public static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) cacheSchemaMap.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz);
            cacheSchemaMap.put(clazz, schema);
        }
        return schema;
    }
}
