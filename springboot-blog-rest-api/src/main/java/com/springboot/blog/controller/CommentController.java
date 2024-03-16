package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService= commentService;
    }

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name ="post_id") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

//    @GetMapping(value = "/{post_id}/comments", params = "version=1")
//    @GetMapping(value = "/{post_id}/comments", headers = "X-API-VERSION=2") ->pass in header
//    @GetMapping(value = "/{post_id}/comments", produces = "application/vnd.gowtham.v1+json") -> pass in header as accept
    @GetMapping("/{post_id}/comments")
    public List<CommentDto> getComments(@PathVariable(name ="post_id") long postId){

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name ="post_id") long postId,
                                     @PathVariable(name ="comment_id") long commentid){

        return new ResponseEntity<>(commentService.getCommentsByCommentId(postId, commentid), HttpStatus.OK);
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<CommentDto> getPostById(@Valid @RequestBody CommentDto commentDto,
                                                  @PathVariable(name = "post_id") long postId,
                                                  @PathVariable(name = "comment_id") long commentId){

        return new ResponseEntity<>(commentService.updateComment(commentDto, postId, commentId), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "post_id") long postId,
                                             @PathVariable(name = "comment_id") long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>(commentId+" deleted successfully.", HttpStatus.OK);
    }
}
