package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import java.util.List;

// Класс PostService предназначен для управления постами в системе.
// Он взаимодействует с репозиторием для выполнения операций CRUD (создание, чтение, обновление, удаление).
public class PostService {
    // Переменная, хранящая репозиторий постов
    private final PostRepository repository;

    // Конструктор, инициализирующий репозиторий
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    // Метод для получения всех постов
    public List<Post> all() {
        return repository.all();
    }

    // Метод для получения поста по его ID
    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    // Метод для сохранения поста
    public Post save(Post post) {
        return repository.save(post);
    }

    // Метод для удаления поста по его ID
    public void removeById(long id) {
        repository.removeById(id);
    }
}

