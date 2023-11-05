package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Comment;
import com.testowanieoprogramowaniaprojekt.entities.Post;
import com.testowanieoprogramowaniaprojekt.entities.Subreddit;
import com.testowanieoprogramowaniaprojekt.entities.User;

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

    public static ExampleUser exampleUser3() {
        User author = User
                .builder()
                .id(2L)
                .username("user")
                .password("user")
                .build();

        return new ExampleUser(author);
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