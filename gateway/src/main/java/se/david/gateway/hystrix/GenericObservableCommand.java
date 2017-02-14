package se.david.gateway.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;

public class GenericObservableCommand extends HystrixObservableCommand<String> {
    private final String url;
    private final RestTemplate restTemplate;
    private final String content;

    public GenericObservableCommand(String groupName, RestTemplate restTemplate, final String url, final String content) {
        super(HystrixCommandGroupKey.Factory.asKey(groupName));
        this.restTemplate = restTemplate;
        this.url = url;
        this.content = content;
    }

    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here

                        HttpHeaders headers = new HttpHeaders();
                        HttpEntity entity = new HttpEntity(headers);

                        ResponseEntity<String> response = restTemplate.exchange("url",
                                HttpMethod.GET,
                                entity,
                                String.class,
                                content
                        );

                        observer.onNext(response.getBody());
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } );
    }

    public String getResult() throws ExecutionException, InterruptedException {
        return this.observe().toBlocking().toFuture().get();
    }
}
