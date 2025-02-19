package com.example.newspeed.profile.service;

import com.example.newspeed.comment.entity.Comment;
import com.example.newspeed.comment.repository.CommentRepository;
import com.example.newspeed.common.config.PasswordEncoder;
import com.example.newspeed.friend.entity.Friend;
import com.example.newspeed.friend.repository.FriendRepository;
import com.example.newspeed.like.entity.Like;
import com.example.newspeed.like.repository.LikeRepository;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.entity.Post;
import com.example.newspeed.post.repository.PostRepository;
import com.example.newspeed.post.service.PostService;
import com.example.newspeed.user.dto.request.DeletePassword;
import com.example.newspeed.user.dto.response.UserResponse;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final FriendRepository friendRepository;


    @Transactional
    public UserResponse updatePassword(Long id, String oldPassword, String newPassword) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //기존 비밀번호를 틀리게 작성할 경우 에러 발생
        if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"기존 비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        //변경된 비밀번호로 저장
        user.updatePassword(encodedPassword);

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }

    @Transactional
    public UserResponse updateUserName(Long id, String newUserName) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        //변경된 유저 이름 저장
        user.updateUserName(newUserName);

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }

    // 유저가 작성한 게시물 조회
    @Transactional
    public List<PostResponse> getUserPosts(Long userId){
        return postService.getPostByUserId(userId);
    }

    // 유저 계정 삭제
    @Transactional
    public void deleteUserById(Long id, DeletePassword deleteDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재 하지 않습니다"));
        System.out.println(deleteDto);
        //기존 비밀번호를 틀리게 작성할 경우 에러 발생
        if(!passwordEncoder.matches(deleteDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        // 1. 유저가 남긴 댓글 삭제(댓글에 달린 좋아요도 삭제)
        List<Comment> comments = commentRepository.findByUserId(id);
        if(!comments.isEmpty()){
            for (Comment comment : comments) {
                List<Like> commentLikes = likeRepository.findByComment_id(comment.getId());
                if(!commentLikes.isEmpty()){
                    likeRepository.deleteAll(commentLikes);
                }
            }
            commentRepository.deleteAll(comments);
        }

        // 2. 유저가 누른 좋아요 삭제
        List<Like> likes = likeRepository.deleteByUserId(id);
        if (!likes.isEmpty()) {
            likeRepository.deleteAll(likes);
        }

        // 3. 유저가 작성한 게시물 삭제(게시글에 작성한 댓글도 삭제)
        List<Post> posts = postRepository.findByUserId(id);
        if(!posts.isEmpty()){
            for (Post post : posts) {
                List<Like> postLikes = likeRepository.findByPost_PostId(post.getPostId());
                if(!postLikes.isEmpty()) {
                    likeRepository.deleteAll(postLikes);
                }
            }
            for (Post post : posts) {
                List<Comment> postComments = commentRepository.findByPost_PostId(post.getPostId());
                if(!postComments.isEmpty()){
                    commentRepository.deleteAll(postComments);
                }
            }
            postRepository.deleteAll(posts);
        }

        // 4. 유저의 친구 삭제(친구 관계인 사람에서도 삭제)
        List<Friend> friends = friendRepository.findAcceptedFriend(id);
        if(!friends.isEmpty()){
            for (Friend friend : friends) {
                friendRepository.delete(friend);
                friendRepository.deleteByUserIdAndFriendId(friend.getRequester().getId(), friend.getReceiver().getId());
            }
            friendRepository.deleteAll(friends);
        }

        // 5. 유저 계정 삭제
        userRepository.deleteById(id);
    }


}
