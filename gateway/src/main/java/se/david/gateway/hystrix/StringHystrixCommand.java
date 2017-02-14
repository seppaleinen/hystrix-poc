package se.david.gateway.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StringHystrixCommand extends HystrixCommand<String> {
    private final String url;
    private final RestTemplate restTemplate;
    private final String content;

    public StringHystrixCommand(String groupName, RestTemplate restTemplate, final String url, final String content) {
        super(HystrixCommandGroupKey.Factory.asKey(groupName));
        this.restTemplate = restTemplate;
        this.url = url;
        this.content = content;
    }

    protected String run() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.GET,
                entity,
                String.class,
                content
        );

        return response.getBody();
    }

    public String getResult() {
        return this.execute();
    }
}
