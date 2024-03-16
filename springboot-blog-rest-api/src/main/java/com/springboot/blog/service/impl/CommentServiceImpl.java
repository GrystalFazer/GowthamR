package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;

import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper){
        this.commentRepository =commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", +postId)
        );

        comment.setPost(post);

        Comment newComment =commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {


        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    public CommentDto getCommentsByCommentId(long postId, long id) {

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", +postId)
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",id)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long postId, long commentId) {

        Comment newComment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", +postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }

        comment.setName(newComment.getName());
        comment.setEmail(newComment.getEmail());
        comment.setBody(newComment.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", +postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to the post");
        }

        commentRepository.deleteById(commentId);
    }


    private CommentDto mapToDto(Comment newComment){
        CommentDto commentResponse = mapper.map(newComment, CommentDto.class);
//        CommentDto commentResponse = new CommentDto();
//
//        commentResponse.setId(newComment.getId());
//        commentResponse.setName(newComment.getName());
//        commentResponse.setEmail(newComment.getEmail());
//        commentResponse.setBody(newComment.getBody());

        return commentResponse;
    }

    //convert dto to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment =mapper.map(commentDto, Comment.class);
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }
}
