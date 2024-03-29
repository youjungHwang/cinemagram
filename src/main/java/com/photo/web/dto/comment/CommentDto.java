package com.photo.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
public class CommentDto {
    @NotBlank
    private String content;
    @NotNull
    private Long imageId;

    @Builder
    public CommentDto(String content, Long imageId) {
        this.content = content;
        this.imageId = imageId;
    }

}
