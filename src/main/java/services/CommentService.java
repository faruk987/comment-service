package services;

import com.google.gson.Gson;
import controllers.CommentController;
import entities.Comment;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class CommentService extends CommentController {
    public void storeComment(String message, String sender, int matchId){
        Comment comment = new Comment();
        comment.matchId = matchId;
        comment.sender = sender;
        comment.message = message;
        comment.createdon = LocalDateTime.now();
        comment.persist();
    }

    public String getAllJson() {
        return new Gson().toJson(Comment.listAll());
    }

    public void deleteById(long id){
        Comment.deleteById(id);
    }
}
