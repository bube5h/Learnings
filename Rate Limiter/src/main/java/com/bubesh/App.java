package com.bubesh;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Bandwidth limit = Bandwidth.classic(1, Refill.intervally(1, Duration.ofSeconds(2)));
        Bucket bucket = Bucket.builder()
            .addLimit(limit)
            .build();
        bucket.tryConsume(1);     // first request
        Executors.newScheduledThreadPool(1)   // schedule another request for 2 seconds later
            .schedule(() -> bucket.tryConsume(1), 2, TimeUnit.SECONDS);


        System.out.println( "Hello World!" );
    }
}
