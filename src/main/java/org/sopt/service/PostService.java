package org.sopt.service;

import org.sopt.common.utils.IdGenrator;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.common.exception.ErrorMessage.*;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private static final String SAVE_FILE_PATH = "saved_posts.txt";
    private static final String LOAD_FILE_PATH = "load_posts.txt";
    private LocalDateTime updatedAt;

    public void createPost(String title) {
        validateTitle(title);
        validateUpdatedAt();
        Post post = new Post(IdGenrator.generateId(), title);
        updatedAt = LocalDateTime.now();
        postRepository.save(post);
    }

    private void validateUpdatedAt() {
        if(updatedAt != null && Duration.between(updatedAt, LocalDateTime.now()).toMinutes() < 3){
            throw new IllegalStateException(TOO_MANY_REQUESTS.getMessage());
        }
    }

    private void validateTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException(EMPTY_TITLE.getMessage());
        }
        if(title.codePointCount(0, title.length()) > 30){
            throw new IllegalArgumentException(INVALID_TITLE_LENGTH.getMessage());
        }
        if(postRepository.isExistByTitle(title)){
            throw new IllegalArgumentException(TITLE_ALREADY_EXISTS.getMessage());
        }
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public void getPostById(int id) {
        Post post = postRepository.findById(id);
        if(post == null){
            throw new IllegalArgumentException(POST_NOT_FOUND.getMessage());
        }
        System.out.println("📄 게시글 상세 내용:");
        System.out.println("-------------------------------------");
        System.out.printf("🆔 ID: %d\n", post.getId());
        System.out.printf("📌 제목: %s\n", post.getTitle());
        System.out.println("-------------------------------------");
    }

    public boolean deletePostById(int id) {
        return postRepository.deleteById(id);
    }

    public boolean updatePost(int updateId, String newTitle){
        Post findPost = postRepository.findById(updateId);
        if(findPost == null){
            return false;
        }
        findPost.setTitle(newTitle);
        return true;
    }

    public List<Post> getAllPostByKeyword(String keyword){
        return postRepository.findAllByKeyword(keyword);
    }

    public void savePostsToFile() throws IOException {
        List<Post> posts = postRepository.findAll();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SAVE_FILE_PATH))) {
            for (Post post : posts) {
                bw.write(post.getTitle());
                bw.newLine();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void loadPostsFromFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(LOAD_FILE_PATH))) {
            String title;
            while ((title = br.readLine()) != null) {
                validateTitle(title);

                Post post = new Post(IdGenrator.generateId(), title);
                postRepository.save(post);
            }
        }
    }




}
