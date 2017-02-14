package se.david.gateway.hystrix;

import java.util.concurrent.ExecutionException;

public interface CommandInterface {
    String getResult() throws ExecutionException, InterruptedException;
}
