package com.photo.web.dto.comment;

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
    private Integer imageId;
}
