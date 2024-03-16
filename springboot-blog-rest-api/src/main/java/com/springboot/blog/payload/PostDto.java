package com.springboot.blog.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {

    private Long id;
    @NotEmpty
    @Size(min = 2,message = "Post title should have at least 2 characters")
    @Schema(
            description = "Blog Post Title"
    )
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10,message = "Post title should have at least 10 characters")
    private String description;
    @NotEmpty
    @Schema(
            description = "Blog Post Content"
    )
    private String content;
    @Schema(
            description = "Blog Post Comments"
    )
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}
