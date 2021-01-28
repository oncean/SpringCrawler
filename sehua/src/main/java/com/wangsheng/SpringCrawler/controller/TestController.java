package com.wangsheng.SpringCrawler.controller;

import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
public class TestController {

    @Autowired
    private ScanService scanService;

    @GetMapping(value="/start1",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> test1(){
        return Flux.interval(Duration.ofMillis(1000))
                .map(l->System.currentTimeMillis()).log();
    }

    @GetMapping(value = "/start2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux(){
        Flux<String> result = Flux.fromStream(IntStream.range(1,5).mapToObj(i->{
            try {
                TimeUnit.SECONDS.sleep(i);
            }catch (InterruptedException e){}
            return "Flux data --" + i;
        }));
        return result;
    }
}
