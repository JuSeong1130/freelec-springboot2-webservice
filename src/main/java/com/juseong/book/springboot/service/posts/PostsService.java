package com.juseong.book.springboot.service.posts;


import com.juseong.book.springboot.domain.posts.Posts;
import com.juseong.book.springboot.domain.posts.PostsRepository;
import com.juseong.book.springboot.web.dto.PostsListResponseDto;
import com.juseong.book.springboot.web.dto.PostsResponseDto;
import com.juseong.book.springboot.web.dto.PostsSaveRequestDto;
import com.juseong.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


//Page 105
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    //Page 105 END

    //Page 113

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent()); //더티 체킹에 의해 업데이트됨

        return id;
    }


    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDecs().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
//Page 113 END

    //Page 159
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

}