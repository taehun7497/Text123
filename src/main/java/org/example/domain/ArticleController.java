package org.example.domain;

import org.example.base.CommonUtil;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Scanner;

// Model - Controller - View
public class ArticleController { // Model + Controller

    CommonUtil commonUtil = new CommonUtil();
    ArticleView articleView = new ArticleView();
    ArticleRepository articleRepository = new ArticleRepository();
    Scanner scan = commonUtil.getScanner();
    int WRONG_VALUE = -1;
    HashMap<String, String> users = new HashMap<>();
    HashMap<String, String> nicknames = new HashMap<>();
    String currentUser = null;


    public void login() {

        System.out.print("아이디 : ");
        String userid = scan.nextLine();
        System.out.print("비밀번호 : ");
        String password = scan.nextLine();

        if (users.containsKey(userid) && users.get(userid).equals(password)) {
            this.currentUser = userid;

            System.out.println(nicknames.get(userid) + "님 환영합니다!");
        } else {
            System.out.println("비밀번호를 틀렸거나 잘못된 회원정보입니다.");
        }
    }

    public void signup() {
        System.out.println("==== 회원 가입을 진행합니다 ====");
        System.out.print("아이디를 입력해주세요 : ");
        String userid = scan.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String password = scan.nextLine();
        System.out.print("이름을 입력해주세요 : ");
        String name = scan.nextLine();

        users.put(userid, password);
        nicknames.put(userid, name);
        System.out.println("==== 회원가입이 완료되었습니다. ====");
    }

    public void search() {
        // 검색어를 입력
        System.out.print("검색 키워드를 입력해주세요 :");
        String keyword = scan.nextLine();
        ArrayList<Article> searchedList = articleRepository.findArticleByKeyword(keyword);

        articleView.printArticleList(searchedList);
    }

    public void detail() {
        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");

        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }

        Article article = articleRepository.findArticleById(inputId);

        if (article == null) {
            System.out.println("없는 게시물입니다.");
            return;
        }

        article.increaseHit();
        articleView.printArticleDetail(article);

    }

    public void delete() {

        System.out.print("삭제할 게시물 번호를 입력해주세요 : ");

        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }

        Article article = articleRepository.findArticleById(inputId);

        if (article == null) {
            System.out.println("없는 게시물입니다.");
            return;
        }

        articleRepository.deleteArticle(article);
        System.out.printf("%d 게시물이 삭제되었습니다.\n", inputId);
    }

    public void update() {
        System.out.print("수정할 게시물 번호를 입력해주세요 : ");

        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }

        Article article = articleRepository.findArticleById(inputId);

        if (article == null) {
            System.out.println("없는 게시물입니다.");
            return;
        }

        System.out.print("새로운 제목을 입력해주세요 : ");
        String newTitle = scan.nextLine();

        System.out.print("새로운 내용을 입력해주세요 : ");
        String newBody = scan.nextLine();

        articleRepository.updateArticle(article, newTitle, newBody);
        System.out.printf("%d번 게시물이 수정되었습니다.\n", inputId);
    }

    public void list() {
        ArrayList<Article> articleList = articleRepository.findAll();
        articleView.printArticleList(articleList); // 전체 출력 -> 전체 저장소 넘기기
    }

    public void add() {

        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scan.nextLine();

        System.out.print("게시물 내용을 입력해주세요 : ");
        String body = scan.nextLine();

        articleRepository.saveArticle(title, body);
        System.out.println("게시물이 등록되었습니다.");

    }

    private int getParamAsInt(String param, int defaultValue) {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
            return defaultValue;
        }
    }
}
