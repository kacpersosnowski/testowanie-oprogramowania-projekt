package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Post;

import java.util.stream.Stream;

public record InvalidPostList(Stream<Post> invalidPosts) {
}
