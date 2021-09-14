package wang.lonelymoon.scaffold.common.util;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class HttpUtils {

    @Resource
    private Gson gson;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient();

    public String get(String url, Multimap<String, String> headers, Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (!headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (MapUtil.isNotEmpty(params)) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        Request request = builder
                .url(httpBuilder.build())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    public String postJson(String url, Multimap<String, String> headers, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request.Builder builder = new Request.Builder();
        if (!headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }
        Request request = builder
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }


}
