package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Comment;

import java.util.stream.Stream;

public record InvalidCommentsList(Stream<Comment> invalidComments) {
}
