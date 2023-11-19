package com.testowanieoprogramowaniaprojekt.testData;

import com.testowanieoprogramowaniaprojekt.entities.Vote;

import java.util.stream.Stream;

public record InvalidVoteList(Stream<Vote> invalidVoteList) {
}
