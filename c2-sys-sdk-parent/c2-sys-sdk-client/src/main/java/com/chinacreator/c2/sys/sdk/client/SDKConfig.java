package com.chinacreator.c2.sys.sdk.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.oauth2.common.CredentialConfiguration;

/**
 * 远程服务器信息，单独定义出来是为了在没有配置服务器地址的情况下让Spring初始化时可以报错
 * 
 * @author Vurt
 */
@Service
public class SDKConfig {
  private String host;

  public String getHost() {
    if(StringUtils.isEmpty(host)){
      String url = CredentialConfiguration.getAuthorizationServerInnerUrl();
      if (StringUtils.endsWith(url,"/")) {
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
    if(path.startsWith("/")){
      path= path.substring(1);
    }
    return getHost() + "/" + path;
  }
}
