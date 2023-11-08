package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {

    private TestDataBuilder() {}

    public static ExampleUser exampleUser() {
        User author = User
                .builder()
                .id(1L)
                .username("author")
                .password("pass")
                .build();

        return new ExampleUser(author);
    }
  
    public static ExampleUser exampleUser2() {
          User author = User
                  .builder()
                  .id(1L)
                  .username("ala")
                  .password("kot")
                  .build();

          return new ExampleUser(author);
      }

    public static ExampleUserList exampleUserList(int size) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            User user = User
                    .builder()
                    .id((long) i)
                    .username("author")
                    .password("pass")
                    .build();
            userList.add(user);
        }
        return new ExampleUserList(userList);
    }

    public static InvalidUserList invalidUserList() {
        List<User> invalidUserList = exampleUserList(2).userList();
        invalidUserList.get(0).setUsername(null);
        invalidUserList.get(1).setPassword(null);
        return new InvalidUserList(invalidUserList);
    }

    public static ExampleSubreddit exampleSubreddit() {
        Subreddit subreddit = Subreddit
                .builder()
                .id(3L)
                .name("subreddit")
                .user(TestDataBuilder.exampleUser().user())
                .build();

        return new ExampleSubreddit(subreddit);
    }

    public static ExampleSubreddit exampleSubreddit2() {
        Subreddit subreddit = Subreddit
                .builder()
                .id(2L)
                .name("subreddit2")
                .user(TestDataBuilder.exampleUser().user())
                .build();

        return new ExampleSubreddit(subreddit);
    }

    public static ExampleSubreddit exampleSubreddit3() {
        Subreddit subreddit = Subreddit
                .builder()
                .id(1L)
                .name("subreddit 2")
                .user(TestDataBuilder.exampleUser().user())
                .build();

        return new ExampleSubreddit(subreddit);
    }

    public static InvalidSubredditsList invalidSubredditsList() {
        Subreddit subreddit1 = Subreddit
                .builder()
                .id(1L)
                .name(null)
                .user(TestDataBuilder.exampleUser().user())
                .build();

        Subreddit subreddit2 = Subreddit
                .builder()
                .id(1L)
                .name("subreddit")
                .user(null)
                .build();

        return new InvalidSubredditsList(Stream.of(subreddit1, subreddit2));
    }

    public static ExamplePost examplePost() {
        Post post = Post
                .builder()
                .id(1L)
                .title("postTitle")
                .description("postDescription")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .build();

        return new ExamplePost(post);
    }

    public static ExamplePost examplePost2() {
        Post post = Post.builder()
                .id(2L)
                .title("post title 2")
                .description("post description")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .build();

        return new ExamplePost(post);
    }

    public static ExamplePost examplePost3() {
        Post post = Post.builder()
                .id(3L)
                .title("post title 3")
                .description("post description #3")
                .author(TestDataBuilder.exampleUser2().user())
                .subreddit(TestDataBuilder.exampleSubreddit2().subreddit())
                .build();

        return new ExamplePost(post);
    }

    public static ExampleComment exampleComment() {
        Comment comment = Comment
                .builder()
                .id(1L)
                .content("Comment 1")
                .author(TestDataBuilder.exampleUser().user())
                .post(TestDataBuilder.examplePost().post())
                .build();

        return new ExampleComment(comment);
    }

    public static ExampleComment exampleComment2() {
        Comment comment = Comment
                .builder()
                .id(2L)
                .content("Comment 2")
                .author(TestDataBuilder.exampleUser().user())
                .post(TestDataBuilder.examplePost().post())
                .build();

        return new ExampleComment(comment);
    }

    public static ExampleComment exampleComment3() {
        Comment comment = Comment
                .builder()
                .id(3L)
                .content("Comment 3")
                .author(TestDataBuilder.exampleUser().user())
                .post(TestDataBuilder.examplePost().post())
                .build();

        return new ExampleComment(comment);
    }

    public static InvalidPostList invalidPostList() {
        Post post1 = Post.builder()
                .id(1L)
                .title(null)
                .description("test")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .comments(null)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title(" ")
                .description("test")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .comments(null)
                .build();

        Post post3 = Post.builder()
                .id(3L)
                .title("title")
                .description("")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .comments(null)
                .build();

        Post post4 = Post.builder()
                .id(4L)
                .title("title")
                .description(null)
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .comments(null)
                .build();

        Post post5 = Post.builder()
                .id(1L)
                .title("title")
                .description("test")
                .author(null)
                .subreddit(TestDataBuilder.exampleSubreddit().subreddit())
                .comments(null)
                .build();

        Post post6 = Post.builder()
                .id(1L)
                .title("title")
                .description("test")
                .author(TestDataBuilder.exampleUser().user())
                .subreddit(null)
                .comments(null)
                .build();

        return new InvalidPostList(Stream.of(post1, post2, post3, post4, post5, post6, null));
    }

    public static InvalidCommentsList invalidCommentsList() {
        Comment comment = Comment
                .builder()
                .id(3L)
                .content(null)
                .author(TestDataBuilder.exampleUser().user())
                .post(TestDataBuilder.examplePost().post())
                .build();

        Comment comment2 = Comment
                .builder()
                .id(3L)
                .content("")
                .author(TestDataBuilder.exampleUser().user())
                .post(TestDataBuilder.examplePost().post())
                .build();

        Comment comment3 = Comment
                .builder()
                .id(3L)
                .content("Comment")
                .author(null)
                .post(TestDataBuilder.examplePost().post())
                .build();

        Comment comment4 = Comment
                .builder()
                .id(3L)
                .content("Comment")
                .author(TestDataBuilder.exampleUser().user())
                .post(null)
                .build();

        return new InvalidCommentsList(Stream.of(comment, comment2, comment3, comment4));
    }
}
