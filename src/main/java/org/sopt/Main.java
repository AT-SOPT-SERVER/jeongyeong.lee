package org.sopt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.sopt.controller.PostController;
import org.sopt.domain.Post;

public class Main {
    private static final String SAVE_FILE_PATH = "saved_posts.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PostController controller = new PostController();

        try (FileWriter fw = new FileWriter(SAVE_FILE_PATH, false)) {
            fw.write("");
        } catch (IOException e) {
            System.out.println("⚠️ 파일 초기화 중 오류 발생: " + e.getMessage());
        }

        printWelcome();

        while (true) {
            printMenu();

            System.out.print("👉 선택: ");
            String input = scanner.nextLine();

            try {
                switch (input) {
                    case "1":
                        controller.createPost();
                        break;

                    case "2":
                        for (Post post : controller.getAllPosts()) {
                            System.out.printf("🆔 %d | 📌 제목: %s\n", post.getId(), post.getTitle());
                        }
                        break;

                    case "3":
                        controller.getPostDetailById();
                        break;

                    case "4":
                        controller.updatePostTitle();
                        break;

                    case "5":
                        controller.deletePostById();
                        break;

                    case "6":
                        controller.searchPostsByKeyword();
                        break;

                    case "7":
                        controller.savePostsToFile();
                        break;

                    case "8":
                        controller.loadPostsFromFile();
                        break;

                    case "0":
                        System.out.println("\n👋 프로그램을 종료합니다. 감사합니다!");
                        return;

                    default:
                        System.out.println("⚠️ 잘못된 입력입니다. 다시 시도해주세요.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printWelcome() {
        System.out.println("=====================================");
        System.out.println("📌  자바 게시판 프로그램에 오신 것을 환영합니다!");
        System.out.println("=====================================\n");
    }

    private static void printMenu() {
        System.out.println("\n================ 메뉴 ================");
        System.out.println("1️⃣  게시글 작성");
        System.out.println("2️⃣  전체 게시글 조회");
        System.out.println("3️⃣  게시글 상세 조회");
        System.out.println("4️⃣  게시글 수정");
        System.out.println("5️⃣  게시글 삭제");
        System.out.println("6️⃣  게시글 검색");
        System.out.println("7️⃣  게시글 저장");
        System.out.println("8️⃣  게시글 불러오기");
        System.out.println("0️⃣  프로그램 종료");
        System.out.println("=====================================");
    }
}
