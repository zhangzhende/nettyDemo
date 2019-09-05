package com.zzd.niodemo.nettyprotocol.privateprotocol;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @Description MarShalling工厂类
 * @ClassName MarShallingCodeCFactory
 * @Author zzd
 * @Create 2019/9/3 9:24
 * @Version 1.0
 **/
public final class MarShallingCodeCFactory {


    public static Marshaller buildMarshaller() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
        return marshaller;
    }

    protected static Unmarshaller buildUnMarshalling() throws IOException {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final Unmarshaller unmarshaller = marshallerFactory
                .createUnmarshaller(configuration);
        return unmarshaller;

    }

    public static NettyMarshallingDecoder buildMarshallingDecoder() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        NettyMarshallingDecoder decoder = new NettyMarshallingDecoder(provider, 1024 << 2);
        return decoder;
    }

    public static NettyMarshallingEncoder buildMarshallingEncoder() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        NettyMarshallingEncoder encoder = new NettyMarshallingEncoder(provider);
        return encoder;
    }

}
