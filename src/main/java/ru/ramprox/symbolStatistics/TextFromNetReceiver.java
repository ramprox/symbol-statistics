package ru.ramprox.symbolStatistics;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TextFromNetReceiver {

    private static final String urlPattern = "http://numbersapi.com/<number>/trivia";

    private final HttpClient httpClient;

    public TextFromNetReceiver(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String receive(String number) {
        HttpRequest request = buildRequest(number);
        HttpResponse<String> response = sendRequest(request);
        System.out.println(response);
        String result = response.body();
        if(result.isBlank()) {
            String message = "Получена пустая строка";
            throw new RuntimeException(message);
        }
        return result;
    }

    private HttpRequest buildRequest(String number) {
        try {
            String url = urlPattern.replace("<number>", number);
            return HttpRequest.newBuilder(new URI(url)).GET().build();
        } catch (URISyntaxException ex) {
            String message = String.format("Неправильный синтаксис URI: %s%n", ex.getMessage());
            throw new RuntimeException(message, ex);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException ex) {
            String message = String.format("Ошибка соединения с ресурсом %s", request.uri());
            throw new RuntimeException(message, ex);
        } catch (IOException e) {
            String message = String.format("Ошибка при выполнении запроса: %s%n", e.getMessage());
            throw new RuntimeException(message, e);
        } catch (InterruptedException e) {
            String message = String.format("Запрос прерван: %s%n", e.getMessage());
            throw new RuntimeException(message, e);
        }
    }

}
