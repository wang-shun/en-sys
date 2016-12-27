package com.chinacreator.c2.sys.sdk.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinacreator.asp.comp.sys.oauth2.common.CredentialConfiguration;
import com.chinacreator.asp.comp.sys.oauth2.common.CredentialStore;
import com.chinacreator.c2.web.util.RestUtils;

/**
 * 远程服务器信息，单独定义出来是为了在没有配置服务器地址的情况下让Spring初始化时可以报错
 * 
 * @author Vurt
 */
@Service
public class SDKUtils {
  private String host;

  public String getHost() {
    if (StringUtils.isEmpty(host)) {
      String url = CredentialConfiguration.getAuthorizationServerInnerUrl();
      if (StringUtils.endsWith(url, "/")) {
        url = url.substring(0, url.length() - 1);
      }
      this.host = url;
    }
    return this.host;
  }

  /**
   * 获取访问服务器的完整url
   * 
   * @param path API相对路径
   */
  public String getUrl(String path) {
    if (path == null || path == "") {
      return getHost();
    }
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    return getHost() + "/" + path;
  }

  private RestTemplate restTemplate;

  /**
   * 获取跟SDK服务器通讯的RestTemplate，在发请求时会在Header中带上当前用户的身份信息
   * @return
   */
  public RestTemplate geRestTemplate() {
    if (restTemplate == null) {
      restTemplate = RestUtils.createRestTemplate();
      List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
      if (interceptors == null) {
        interceptors = new ArrayList<ClientHttpRequestInterceptor>();
      }
      interceptors.add(new RestAuthorizationHeaderInterceptor());
      restTemplate.setInterceptors(interceptors);
    }
    return restTemplate;
  }
}
class RestAuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().add(javax.ws.rs.core.HttpHeaders.AUTHORIZATION,
        "Bearer " + CredentialStore.getCurrCredential().getAccessToken());
    return execution.execute(request, body);
  }
}
