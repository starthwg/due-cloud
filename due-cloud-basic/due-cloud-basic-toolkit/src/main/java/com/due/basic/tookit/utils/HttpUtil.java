package com.due.basic.tookit.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.due.basic.tookit.doamin.Result;
import com.due.basic.tookit.enums.ErrorEnum;
import com.due.basic.tookit.exception.LogicAssert;
import com.due.basic.tookit.exception.LogicException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

@Slf4j
public class HttpUtil {
    public final static String P_BEARER_PREFIX = "Bearer "; // Authentication中的前缀

    /**
     * 获取请求IP地址
     *
     * @param request HTTP请求
     * @return 请求IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) return null;
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("HTTP_X_REAL_IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getRemoteAddr();
        }
        if (null != ip) {
            int idx = ip.indexOf(",");
            if (idx > 0) {
                ip = ip.substring(0, idx).trim();
            }
        }
        //if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
        //String[] ips = ip.split(",");
        //ip = ips[ips.length - 1];
        //ip = ips[0];
        //}
        return StringUtils.substringBefore(ip, ",");
    }

    public static Result<String> post(RestTemplate restTemplate, String url, JSONObject header, JSONObject entity, boolean isForm) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_ERROR, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }

            HttpEntity<?> httpEntity;
            MultiValueMap<String, Object> multiValueMap;
            if (entity == null || entity.isEmpty()) {
                httpEntity = new HttpEntity<>(headers);
            } else {
                if (isForm) {
                    multiValueMap = new LinkedMultiValueMap<>();
                    for (Entry<String, Object> entry : entity.entrySet()) {
                        multiValueMap.add(entry.getKey(), entry.getValue());
                    }
                    httpEntity = new HttpEntity<>(multiValueMap, headers);
                } else {
                    httpEntity = new HttpEntity<>(entity, headers);
                }
            }
            log.info("【请求地址】：{}", url);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            String response = responseEntity.getBody();
            log.info("【响应状码】：{}", responseEntity.getStatusCode());
            log.info("【响应数据】：{}", response);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.SERVICE_ERROR, "请求数据失败：" + url);
            return response;
        });
    }

    public static Result<String> auth(RestTemplate restTemplate, String url, JSONObject header, JSONObject entity, boolean isForm) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_ERROR, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }

            HttpEntity<?> httpEntity;
            MultiValueMap<String, Object> multiValueMap;
            if (entity == null || entity.isEmpty()) {
                httpEntity = new HttpEntity<>(headers);
            } else {
                if (isForm) {
                    multiValueMap = new LinkedMultiValueMap<>();
                    for (Entry<String, Object> entry : entity.entrySet()) {
                        multiValueMap.add(entry.getKey(), entry.getValue());
                    }
                    httpEntity = new HttpEntity<>(multiValueMap, headers);
                } else {
                    httpEntity = new HttpEntity<>(entity, headers);
                }
            }
            log.info("【请求地址】：{}", url);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            String response = responseEntity.getBody();
            log.info("【响应状码】：{}", responseEntity.getStatusCode());
            log.info("【响应数据】：{}", response);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.AUTHORIZE_INVALID, "请求数据失败：" + url);
            return response;
        });
    }

    public static Result<String> post(RestTemplate restTemplate, String url, JSONObject header, String entity) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_ERROR, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }

            HttpEntity<String> httpEntity;
            if (entity == null || entity.isEmpty()) {
                httpEntity = new HttpEntity<>(headers);
            } else {
                httpEntity = new HttpEntity<>(entity, headers);
            }
            log.info("【请求地址】：{}", url);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            String response = responseEntity.getBody();
            log.info("【响应数据】：{}", response);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.SERVICE_ERROR, "请求数据失败：" + url);
            return response;
        });
    }

    public static Result<String> exchange(RestTemplate restTemplate, HttpMethod method, String url, JSONObject header, JSONObject entity, boolean isForm) {
        return Result.exec(() -> {
            LogicAssert.isNull(method, ErrorEnum.PARAMETER_ERROR);
            if (method.equals(HttpMethod.GET)) {
                return get(restTemplate, url, header, entity).getData();
            } else if (method.equals(HttpMethod.POST)) {
                return post(restTemplate, url, header, entity, isForm).getData();
            } else {
                throw new LogicException(ErrorEnum.PARAMETER_ERROR);
            }
        });
    }

    public static Result<byte[]> postByte(RestTemplate restTemplate, String url, JSONObject header, String entity) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_ERROR, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }

            HttpEntity<?> httpEntity;
            if (entity == null || entity.isEmpty()) {
                httpEntity = new HttpEntity<>(headers);
            } else {
                httpEntity = new HttpEntity<>(entity, headers);
            }
            log.info("【请求地址】：{}", url);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, byte[].class);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.SERVICE_ERROR, "请求数据失败：" + url);
            byte[] response = responseEntity.getBody();
            log.info("【响应数据】：{}", Optional.ofNullable(response).map(String::new).orElse(null));
            return response;
        });
    }



    public static Result<String> get(RestTemplate restTemplate, String url, JSONObject header, String entity) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_INVALID, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            HttpEntity<String> httpEntity;
            if (LogicUtil.isAllBlank(entity)) {
                httpEntity = new HttpEntity<>(headers);
            } else {
                httpEntity = new HttpEntity<>(entity, headers);
            }
            log.info("【请求地址】：{}", url);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.SERVICE_ERROR, "请求数据失败：" + url);
            log.info("【响应数据】：{}", JSONObject.toJSON(responseEntity.getBody()));
            return responseEntity.getBody();
        });
    }

    public static Result<String> get(RestTemplate restTemplate, String url, JSONObject header, JSONObject entity) {
        return Result.exec(() -> {
            LogicAssert.isBlank(url, ErrorEnum.PARAMETER_INVALID, "请求地址为空");
            HttpHeaders headers = new HttpHeaders();
            if (header != null && !header.isEmpty()) {
                header.keySet().forEach(k -> {
                    headers.add(k, header.getString(k));
                });
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            String strUrl = url;
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            if (entity != null && !entity.isEmpty()) {
                strUrl = StringUtils.indexOf(strUrl, "?") != -1 ? StringUtils.join(strUrl, "&") : StringUtils.join(strUrl, "?");
                strUrl = StringUtils.join(strUrl, StringUtils.join(entity.entrySet().stream().map(e -> StringUtils.join(e.getKey(), "=", e.getValue())).toArray(), "&"));
            }
            log.info("【请求地址】：{}", strUrl);
            log.info("【请求参数】：{}", entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(strUrl, HttpMethod.GET, httpEntity, String.class);
            LogicAssert.isTrue(responseEntity.getStatusCode() != HttpStatus.OK, ErrorEnum.SERVICE_ERROR, "请求数据失败：" + url);
            log.info("【响应数据】：{}", JSONObject.toJSON(responseEntity.getBody()));
            return responseEntity.getBody();
        });
    }

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("access_token");
        }
        return StringUtils.removeStartIgnoreCase(token, P_BEARER_PREFIX);
    }

    public static JSONObject parameter2Object(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters == null || parameters.isEmpty()) return null;
        JSONObject result = new JSONObject(true);
        for (Entry<String, String[]> entry : parameters.entrySet()) {
            if (entry == null) continue;
            String[] values = entry.getValue();
            if (values == null) {
                result.put(entry.getKey(), null);
            } else if (values.length > 1) {
                result.put(entry.getKey(), JSONArray.toJSON(values));
            } else {
                result.put(entry.getKey(), values[0]);
            }
        }
        return result;
    }

    public static JSONObject stream2Object(HttpServletRequest request) {
        byte[] bytes = new byte[1024];
        int len;
        try (InputStream in = request.getInputStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            while ((len = in.read(bytes, 0, 1024)) != -1) {
                bos.write(bytes, 0, len);
            }
            return JSON.parseObject(bos.toString(), Feature.OrderedField);
        } catch (Exception e) {
            log.error("获取请求数据失败", e);
            return null;
        }
    }

    public static <T> T stream2Object(HttpServletRequest request, Class<T> clazz) {
        byte[] bytes = new byte[1024];
        int len;
        try (InputStream in = request.getInputStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            while ((len = in.read(bytes, 0, 1024)) != -1) {
                bos.write(bytes, 0, len);
            }
            return JSON.parseObject(bos.toString(), clazz, Feature.OrderedField);
        } catch (Exception e) {
            log.error("获取请求数据失败", e);
            return null;
        }
    }
}
