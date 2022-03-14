package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * @Author: monkey
 * @Description: 合约WebSocket例子
 * @Date: 2022/3/14 15:32
 * @Version: 1.0
 */
public class OpenAipWsDemo {
    /**
     * 版本号
     */
    private final static int VERSION = 2;
    /**
     * 加密方法
     */
    private final static String HmacSHA256 = "HmacSHA256";
    /**
     * 请求地址
     */
    private final static String HOST = "perpetual-wss.monkey.com";

    private final static String httpMethod = "wss";

    public static void main(String[] args) {
        String apiKey = "f409061482cc4d80968b2657b8451701";
        String secretKey = "167CA399DE36319B0DB2A2D8BDD15BE7";
        String uri = "/wss";
        long timestamp = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>(4);
        params.put("AccessKeyId", apiKey);
        params.put("SignatureVersion", VERSION);
        params.put("SignatureMethod", HmacSHA256);
        params.put("Timestamp", timestamp);
        String sinature = getSignature(secretKey, HOST, uri, httpMethod, params);
        System.out.println("sinagure:{}" + sinature);//Zw+MN2XG54s5LT9rcHLh1sDJ3XGbVTdRGrj+73XH8LI=
        System.out.println("time:" + timestamp);//1592476590217
    }

    /**
     * 签名
     *
     * @param apiSecret
     * @param host
     * @param uri
     * @param httpMethod
     * @param params
     * @return
     * @throws Exception
     */
    public static String getSignature(String apiSecret, String host, String uri, String httpMethod, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(httpMethod.toUpperCase()).append('\n')
                .append(host.toLowerCase()).append('\n')
                .append(uri).append('\n');
        SortedMap<String, Object> map = new TreeMap<>(params);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            try {
                sb.append(key).append('=').append(URLEncoder.encode(value, "UTF-8")).append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(HmacSHA256);
            SecretKeySpec secKey =
                    new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), HmacSHA256);
            hmacSha256.init(secKey);
        } catch (Exception e) {
            return null;
        }
        String payload = sb.toString();
        System.out.println(payload);
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        //需要对签名进行base64的编码
        String actualSign = Base64.getEncoder().encodeToString(hash);
        actualSign = actualSign.replace("\n", "");
        return actualSign;
    }


}
