package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name= "CRUD REST APIs for Post Resource"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 Created"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Posts Rest API",
            description = "Get All Posts REST API is used to retrieve all the posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status Code 200 SUCCESS"
    )
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy);
    }

    @Operation(
            summary = "Get Post By Id Rest API",
            description = "Get Post By Id  REST API is used to retrieve a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status Code 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){

        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Post By Id Rest API",
            description = "Update Post By Id  REST API is used to update a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status Code 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id){

        return new ResponseEntity<>(postService.updatePost(postDto,id), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post By Id Rest API",
            description = "Delete Post By Id  REST API is used to delete a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status Code 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(id+" deleted successfully.", HttpStatus.OK);
    }

    @Operation(
            summary = "Get Posts  By CategoryId Rest API",
            description = "Get Posts By CategoryId  REST API is used to retrieve posts that belongs to one category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status Code 200 SUCCESS"
    )
    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable(name = "category_id") long category_id){

        return ResponseEntity.ok(postService.getPostByCategoryId(category_id));
    }
}
