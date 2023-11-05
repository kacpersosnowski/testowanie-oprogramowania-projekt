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

    public static ExampleSubreddit exampleSubreddit() {
        Subreddit subreddit = Subreddit
                .builder()
                .id(1L)
                .name("subreddit")
                .user(TestDataBuilder.exampleUser().user())
                .build();

        return new ExampleSubreddit(subreddit);
    }

    public static ExampleSubreddit exampleSubreddit2() {
        Subreddit subreddit = Subreddit
                .builder()
                .id(1L)
                .name("subreddit 1")
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
