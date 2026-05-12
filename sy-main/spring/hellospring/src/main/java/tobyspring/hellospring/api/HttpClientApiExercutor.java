package tobyspring.hellospring.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApiExercutor implements ApiExecutor {
    @Override
    public String execute(URI uri) throws IOException {
      HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            try {
                return HttpClient.newBuilder()
                        .build()
                        .send(request, HttpResponse.BodyHandlers.ofString()).body();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }
}
