package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Subreddit;

import java.util.stream.Stream;


public record InvalidSubredditsList(Stream<Subreddit> invalidSubreddits) {
}
