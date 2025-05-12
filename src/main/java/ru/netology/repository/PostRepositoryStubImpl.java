package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    AtomicLong lastId = new AtomicLong();

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        for (Post post : posts.values()) {
            if (!post.getRemoved()) {
                list.add(post);
            }
        }
        return list;
    }

    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        if(!post.getRemoved()){
            return Optional.ofNullable(post);
        }else {
            throw new NotFoundException("Post with such id doesn't exist or was removed");
        }
    }

    public Post save(Post post) {
        if(post.getId() != 0 && posts.get(post.getId()).getRemoved()){
            throw new NotFoundException("Post with such id doesn't exist or was removed");
        }
        if (post.getId() == 0) {
            post.setId(lastId.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.get(id).setRemoved(true);
    }
}