package com.dudu.wechat.utils;

import com.dudu.wechat.model.BaseRequest;
import com.dudu.wechat.synckey.model.BaseSyncKey;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {

    public static final String LOGIN_BASE_URL = "https://login.wx.qq.com";
    public static final String MAIN_BASE_URL = "https://wx.qq.com";
    public static final String FILE_BASE_URL = "https://file.wx.qq.com";
    public static final String SYNC_BASE_URL = "https://webpush.wx.qq.com";

    public static HashMap<String, String> LOGIN_HEADERS() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        headers.put(
                "Cookie",
                "webwxuvid=e7dbee8941b573cc0f7a5941f41e05f630b450d4a4c1c6e96b572706b9c768118943f33bf4a34aac68fec1e499845d5c"
                        + "; webwx_auth_ticket=CIsBEKXWuuQJGoABEyPuKQKLv97+rehYLeBZt8l8qX6T30w9Yh/cig4m0d2GdiUdpZWSn45oFjnJSw+4CbPoUkvduj/ACp4EjRknN8/q5I7B5Xc1YuDwhyb86+RGe+dbuYaltRayxCNKAzawKsV+B4eXANBj3azhCxKo9GDBlobdnAUAr8mrFTBuslI=");
        return headers;
    }

    public static HashMap<String, String> WX_HEADERS() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        headers.put(
                "extspam",
                "Go8FCIkFEokFCggwMDAwMDAwMRAGGvAESySibk50w5Wb3uTl2c2h64jVVrV7gNs06GFlWplHQbY/5FfiO++1yH4ykCyNPWKXmco+wfQzK5R98D3so7rJ5LmGFvBLjGceleySrc3SOf2Pc1gVehzJgODeS0lDL3/I/0S2SSE98YgKleq6Uqx6ndTy9yaL9qFxJL7eiA/R3SEfTaW1SBoSITIu+EEkXff+Pv8NHOk7N57rcGk1w0ZzRrQDkXTOXFN2iHYIzAAZPIOY45Lsh+A4slpgnDiaOvRtlQYCt97nmPLuTipOJ8Qc5pM7ZsOsAPPrCQL7nK0I7aPrFDF0q4ziUUKettzW8MrAaiVfmbD1/VkmLNVqqZVvBCtRblXb5FHmtS8FxnqCzYP4WFvz3T0TcrOqwLX1M/DQvcHaGGw0B0y4bZMs7lVScGBFxMj3vbFi2SRKbKhaitxHfYHAOAa0X7/MSS0RNAjdwoyGHeOepXOKY+h3iHeqCvgOH6LOifdHf/1aaZNwSkGotYnYScW8Yx63LnSwba7+hESrtPa/huRmB9KWvMCKbDThL/nne14hnL277EDCSocPu3rOSYjuB9gKSOdVmWsj9Dxb/iZIe+S6AiG29Esm+/eUacSba0k8wn5HhHg9d4tIcixrxveflc8vi2/wNQGVFNsGO6tB5WF0xf/plngOvQ1/ivGV/C1Qpdhzznh0ExAVJ6dwzNg7qIEBaw+BzTJTUuRcPk92Sn6QDn2Pu3mpONaEumacjW4w6ipPnPw+g2TfywJjeEcpSZaP4Q3YV5HG8D6UjWA4GSkBKculWpdCMadx0usMomsSS/74QgpYqcPkmamB4nVv1JxczYITIqItIKjD35IGKAUwAA==");
        headers.put("client-version", "2.0.0");
        headers.put(
                "Cookie",
                "webwxuvid=e7dbee8941b573cc0f7a5941f41e05f630b450d4a4c1c6e96b572706b9c768118943f33bf4a34aac68fec1e499845d5c"
                        + ";webwx_auth_ticket="
                        + SharedPreferencesUtil.getData(SharedPreferencesUtil.AUTH_TICKET, "")
                        + ";wxloadtime="
                        + SharedPreferencesUtil.getData(SharedPreferencesUtil.LOAD_TIME, "")
                        + ";wxuin="
                        + SharedPreferencesUtil.getData(SharedPreferencesUtil.UIN, 0L)
                        + ";wxsid="
                        + SharedPreferencesUtil.getData(SharedPreferencesUtil.SID, "")
                        + ";webwx_data_ticket="
                        + SharedPreferencesUtil.getData(SharedPreferencesUtil.DATA_TICKET, ""));
        return headers;
    }

    private static Retrofit retrofit(String baseUrl, Headers headers) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request =
                                original.newBuilder()
                                        .headers(headers)
                                        .method(original.method(), original.body())
                                        .build();

                        return chain.proceed(request);
                    }
                });
        //httpClient.followRedirects(false);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
        return retrofit;
    }

    private static Retrofit retrofitLogin(String baseUrl) {
        return retrofit(baseUrl, Headers.of(LOGIN_HEADERS()));
    }

    private static Retrofit retrofitWx(String baseUrl) {
        return retrofit(baseUrl, Headers.of(WX_HEADERS()));
    }


    public static <T> T createLogin(Class<T> t, String baseUrl) {
        return retrofitLogin(baseUrl).create(t);
    }

    public static <T> T createLogin(Class<T> t) {
        return retrofitLogin(LOGIN_BASE_URL).create(t);
    }

    public static <T> T create(Class<T> t, String baseUrl) {
        return retrofitWx(baseUrl).create(t);
    }

    public static <T> T create(Class<T> t) {
        return retrofitWx(MAIN_BASE_URL).create(t);
    }
    
    public static <T> T createSync(Class<T> t) {
        return retrofitWx(SYNC_BASE_URL).create(t);
    }
}
