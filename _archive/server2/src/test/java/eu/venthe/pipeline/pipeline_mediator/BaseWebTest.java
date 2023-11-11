//package eu.venthe.pipeline.pipeline_mediator;
//
//import eu.venthe.pipeline.gerrit_mediator.BaseTest;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//
//import java.util.function.Function;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
//        "logging.level.reactor.netty.channel=debug"
//})
//public class BaseWebTest extends BaseTest {
//    @LocalServerPort
//    Integer port;
//
//    WebClient application;
//
//    @BeforeEach
//    void webClient() {
//        application = WebClient.create("http://localhost:" + port);
//    }
//
//    static <T> Function<ClientResponse, Mono<Tuple2<ClientResponse, T>>> responseWithBody(Class<T> elementClass) {
//        return a -> Mono.zip(Mono.just(a), a.bodyToMono(elementClass));
//    }
//}
