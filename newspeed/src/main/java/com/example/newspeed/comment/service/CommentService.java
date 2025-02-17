package com.example.newspeed.comment.service;

import com.example.newspeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newspeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newspeed.comment.dto.response.CommentResponseDto;
import com.example.newspeed.comment.entity.Comment;
import com.example.newspeed.comment.repository.CommentRepository;
import com.example.newspeed.post.entity.Post;
import com.example.newspeed.post.repository.PostRepository;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto save(Long userId, Long postId, CommentSaveRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Comment comment = new Comment(post, user, dto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(
                comment.getId(),
                user.getId(),
                post.getPostId(),
                comment.getContent());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);
        return comments.stream()
                .map(comment->new CommentResponseDto(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getPost().getPostId(),
                        comment.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getPostId(),
                comment.getContent());
    }

    @Transactional
    public CommentResponseDto update(Long commentId, Long userId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));
        if(!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.update(dto.getContent());
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getPostId(),
                comment.getContent());
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
