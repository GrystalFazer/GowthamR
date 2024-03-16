package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private CategoryRepository categoryRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository,CategoryRepository categoryRepository, ModelMapper mapper){
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        Category category= categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost =postRepository.save(post);

        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> ListOfPosts = posts.getContent();

        List<PostDto> content = ListOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        //return ListOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getPostByCategoryId(long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        List<PostDto> postDtos = posts.stream()
                .map(post -> mapToDto(post))
                .collect(Collectors.toList());

        return postDtos;
    }


    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        Category category= categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getCategoryId()));



        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    //we can use a third party library for converting this i.e.,(ModelMapper)
    //convert entity to dto
    private PostDto mapToDto(Post newPost){

        PostDto postDto = mapper.map(newPost, PostDto.class);

//        PostDto postResponse = new PostDto();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
//
//        return postResponse;
        return postDto;

    }

    //convert dto to entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

//        Post post =new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }
}
