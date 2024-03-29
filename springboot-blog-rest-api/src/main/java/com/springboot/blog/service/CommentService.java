package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsByCommentId(long postId, long id);

    CommentDto updateComment(CommentDto commentDto,long postId,long commentId);

    void deleteCommentById(long postId, long commentId);
}
