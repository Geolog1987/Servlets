package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

//Этот класс представляет собой контроллер, который управляет запросами к сервису постов.
// Он обрабатывает HTTP-запросы, взаимодействует с сервисным слоем для выполнения операций с постами
@Controller
// и возвращает результаты клиенту в формате JSON.
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;//Ссылка на обьект PostService

    public PostController(PostService service) {
        this.service = service;
    }

    // Метод для получения всех постов.
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);//Задает тип содержимого ответа
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));//Возвращает поток записи типа PrintWriter,
        // который можно использовать для вывода символьных данных в ответ.
    }

    // Метод для получения поста по ID.
    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.getById(id);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    // Метод для сохранения поста.
    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    // Метод для удаления поста по ID.
    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var data = service.getById(id);
        service.removeById(id);
        response.getWriter().print(gson.toJson(data));

    }
}
