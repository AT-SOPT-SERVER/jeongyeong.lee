package org.sopt.service;

import org.sopt.common.utils.IdGenrator;
import org.sopt.common.utils.Validator;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.common.exception.ErrorMessage.*;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private static final String SAVE_FILE_PATH = "saved_posts.txt";
    private static final String LOAD_FILE_PATH = "load_posts.txt";
    private LocalDateTime updatedAt;

    public void createPost(String title) {
        Validator.validateTitle(title);
        Validator.validateUpdatedAt(updatedAt);
        Post post = new Post(IdGenrator.generateId(), title);
        updatedAt = LocalDateTime.now();
        postRepository.save(post);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public void getPostDetailById(Long id) {
        Post post = getFindPost(id);
        System.out.println("📄 게시글 상세 내용:");
        System.out.println("-------------------------------------");
        System.out.printf("🆔 ID: %d\n", post.getId());
        System.out.printf("📌 제목: %s\n", post.getTitle());
        System.out.println("-------------------------------------");
    }

    public void deletePostById(Long id) {
        if(!postRepository.deleteById(id)){
            throw new IllegalArgumentException(CANNOT_DELETE.getMessage());
        }
        System.out.println("🗑️ 게시글이 성공적으로 삭제되었습니다.");
    }

    public void updatePost(Long updateId, String newTitle){
        Post findPost = getFindPost(updateId);
        Validator.validateTitle(newTitle);
        findPost.setTitle(newTitle);
        System.out.println("✅ 게시글이 성공적으로 수정되었습니다.");
    }

    private Post getFindPost(Long updateId) {
        return postRepository.findById(updateId).orElseThrow(() -> new IllegalArgumentException(POST_NOT_FOUND.getMessage()));
    }

    public void getAllPostByKeyword(String keyword){
        List<Post> findPosts = postRepository.findAllByKeyword(keyword);
        if (findPosts.isEmpty()) {
            System.out.println("🔍 검색 결과가 없습니다.");
        } else {
            System.out.println("📋 검색 결과:");
            for (Post post : findPosts) {
                System.out.printf("🆔 %d | 📌 제목: %s\n", post.getId(), post.getTitle());
            }
        }
    }

    public void savePostsToFile() throws IOException {
        postRepository.saveToFile(SAVE_FILE_PATH);
        System.out.println("게시글이 파일에 저장되었습니다.");
    }

    public void loadPostsFromFile() throws IOException {
        postRepository.loadFromFile(LOAD_FILE_PATH);
        System.out.println("파일에서 게시글을 불러왔습니다.");
    }

}
