package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
// Класс PostRepository предназначен для управления объектами типа Post.

public class PostRepository {
    private final ConcurrentMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();//создание уникального индефикатора

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    // Этот метод ищет объект типа Post по его ID.
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    // Этот метод сохраняет объект типа Post.
    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = idCounter.incrementAndGet();//генерирует новый id
            post.setId(id);
            posts.put(post.getId(), post);
        } else if (post.getId() != 0) {
            Long currentId = post.getId();
            posts.put(currentId, post);
        }
        return post;
    }

    // Этот метод удаляет объект типа Post по его ID.
    public void removeById(long id) {
        posts.remove(id);
    }
}


