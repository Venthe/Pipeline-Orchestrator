package eu.venthe.platform.application.repositorys.plugin.gerrit;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SimpleStringClient {
    static RestClient mutate(RestClient restClient) {
        return restClient.mutate().messageConverters(httpMessageConverters -> {
            httpMessageConverters.clear();
            HttpMessageConverter<String> converter = new HttpMessageConverter<>() {
                @Override
                public boolean canRead(Class<?> clazz, MediaType mediaType) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                    return mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) && clazz.isAssignableFrom(String.class);
                }

                @Override
                public List<MediaType> getSupportedMediaTypes() {
                    return List.of(MediaType.APPLICATION_JSON);
                }

                @Override
                public String read(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void write(String s, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                    outputMessage.getBody().write(s.getBytes(StandardCharsets.UTF_8));
                }
            };
            httpMessageConverters.add(converter);
        }).build();
    }
}
