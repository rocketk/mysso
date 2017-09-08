package mysso.logout;

import com.alibaba.fastjson.JSON;
import mysso.protocol1.Constants;
import mysso.protocol1.dto.AssertionDto;
import mysso.protocol1.dto.LogoutResultDto;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengyu on 17-9-4.
 */
public class HttpLogoutHandler implements LogoutHandler {
    private Logger log = LoggerFactory.getLogger(getClass());
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    @Override
    public LogoutResultDto logoutViaBackchannel(String url, String token) throws IOException {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair(Constants.SLO_PARAM_TOKEN, token));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            log.trace("sending logout request to service client, logout url: {}, tokenId: {}",
                    url, token);
            response = httpclient.execute(httpPost);
            log.trace("received response, status: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("client-side error");
                LogoutResultDto logoutResultDto = new LogoutResultDto();
                logoutResultDto.setCode(Constants.SLO_CODE_ERROR);
                logoutResultDto.setMessage("client-side error");
                return logoutResultDto;
            }
            HttpEntity entity = response.getEntity();
            InputStream contentStream = entity.getContent();
            log.trace("reading input stream of response to a string");
            String content = IOUtils.toString(contentStream, "UTF-8");
            log.trace("response content: " + content);
            log.trace("parsing the string content to AssertionDto object");
            LogoutResultDto logoutResultDto = JSON.parseObject(content, LogoutResultDto.class);
            EntityUtils.consume(entity);
            return logoutResultDto;
        } catch (Exception e) {
            log.error("an exception occurred when sending validation request, caused by: " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    public void setHttpclient(CloseableHttpClient httpclient) {
        this.httpclient = httpclient;
    }
}
