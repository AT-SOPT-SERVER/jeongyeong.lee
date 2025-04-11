package org.sopt.repository;

import org.sopt.common.utils.IdGenrator;
import org.sopt.common.utils.Validator;
import org.sopt.domain.Post;

import java.io.*;
import java.util.*;

public class PostRepository {
    Map<Long, Post> postMap = new HashMap<>();

    public void save(Post post) {
        postMap.put(post.getId(), post);
    }

    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public boolean deleteById(Long id) {
        return postMap.remove(id) != null;
    }

    public boolean isExistByTitle(String title) {
        return postMap.values().stream().
                anyMatch(post -> post.getTitle().equals(title));
    }

    public List<Post> findAllByKeyword(String keyword) {
        return postMap.values().stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }

    public void saveToFile(String path) throws IOException {
        List<Post> posts = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Post post : posts) {
                writer.write(post.getTitle());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String title;
            while ((title = reader.readLine()) != null) {
                Validator.validateTitle(title);
                Post post = new Post(IdGenrator.generateId(), title);
                save(post);
            }
        }
    }
}
