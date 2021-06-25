package services;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import entities.Comment;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class ForgetUserService {

    @Incoming("deleteUser")
    @Blocking
    @Transactional
    public void deleteGambleHistory(Object username){
        Comment.delete("sender", username);
    }
}
