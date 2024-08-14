package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_PATH_PATTERN = "/api/posts/\\d+";

    @Override
    public void init() {//этот метод вызывается только в том случае, если сервлет впервые загружается в оперативную память
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);//Объект контроллера не создается напрямую, потому что он зависит от сервиса,
        // который в свою очередь зависит от репозитория.
        // Это позволяет следовать принципам инверсии зависимостей и делает код более гибким и тестируемым.
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {//метод для обработки HTTP запроса
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();//Метод из интерфеса HttpServletRequest реализуется контейнером сервлетов.
            //Интерфейс позволяет получать из сервлета сведения о запросе клиента. Метод getRequestURI() возвращает URL.
            final var method = req.getMethod();//Возвращает метод для HTTP запроса.
            // primitive routing
            if (method.equals("GET") && path.equals(API_POSTS)) {
                controller.all(resp);
                return;// Метод service сам по себе ничего не возвращает, так как имеет тип void, но return позволяет прервать дальнейшее выполнение кода метода.
            }
            // Если метод запроса GET и URI соответствует шаблону "/api/posts/д", получаем пост по ID
            if (method.equals("GET") && path.matches(API_POSTS_PATH_PATTERN)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));//parseLong возвращает число из строки,
                controller.getById(id, resp);
                return;
            }
            // Если метод запроса POST и URI "/api/posts", сохраняем новый пост
            if (method.equals("POST") && path.equals("/api/posts")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.save(req.getReader(), resp);
                return;
            }
            // Если метод запроса DELETE и URI соответствует шаблону "/api/posts/д", удаляем пост по ID
            if (method.equals("DELETE") && path.matches(API_POSTS_PATH_PATTERN)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

