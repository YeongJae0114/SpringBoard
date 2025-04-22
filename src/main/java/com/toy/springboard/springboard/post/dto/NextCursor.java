package com.toy.springboard.springboard.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NextCursor {
    private LocalDateTime createdDateCursor;
    private Long cursorId;

    public static NextCursor formEntity(LocalDateTime createdDateCursor, Long cursorId){
        return new NextCursor(createdDateCursor, cursorId);
    }
}
