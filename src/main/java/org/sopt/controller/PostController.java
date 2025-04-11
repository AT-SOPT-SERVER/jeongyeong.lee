package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PostController {
    private final PostService postService = new PostService();
    private final Scanner scanner = new Scanner(System.in);
    public void createPost(){
        System.out.println("\n📝 [게시글 작성]");
        System.out.print("📌 제목을 입력해주세요: ");
        String title = scanner.nextLine();
        postService.createPost(title);
        System.out.println("✅ 게시글이 성공적으로 저장되었습니다!");
    }

    public List<Post> getAllPosts(){
        System.out.println("\n📚 [전체 게시글 조회]");
        return postService.getAllPost();
    }

    public void getPostDetailById(){
        System.out.println("\n🔍 [게시글 상세 조회]");
        System.out.print("📌 조회할 게시글 ID를 입력해주세요: ");
        Long id = Long.parseLong(scanner.nextLine());
        postService.getPostDetailById(id);
    }

    public void deletePostById(){
        System.out.println("\n🗑️ [게시글 삭제]");
        System.out.print("📌 삭제할 게시글 ID를 입력해주세요: ");
        Long deleteId = Long.parseLong(scanner.nextLine());
        postService.deletePostById(deleteId);
    }

    public void updatePostTitle(){
        System.out.println("\n✏️ [게시글 수정]");
        System.out.print("📌 수정할 게시글 ID를 입력해주세요: ");
        Long updateId = Long.parseLong(scanner.nextLine());
        System.out.print("📝 새 제목을 입력해주세요: ");
        String newTitle = scanner.nextLine();
        postService.updatePost(updateId, newTitle);
    }

    public void searchPostsByKeyword(){
        System.out.println("\n🔎 [게시글 검색]");
        System.out.print("검색할 키워드를 입력해주세요: ");
        String keyword = scanner.nextLine();
        postService.getAllPostByKeyword(keyword);
    }

    public void savePostsToFile() throws IOException {
        System.out.println("\n💾 [게시글 파일로 저장]");
        postService.savePostsToFile();
    }

    public void loadPostsFromFile() throws IOException {
        System.out.println("\n📂 [게시글 불러오기]");
        postService.loadPostsFromFile();
    }
}
