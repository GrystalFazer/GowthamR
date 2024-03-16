package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min=1,message = "Minimum 1 character is required")
    private String name;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min=10,message = "Minimum 10 character is required")
    private String body;
}
