package com.example.demo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CommentsDto;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);
}
