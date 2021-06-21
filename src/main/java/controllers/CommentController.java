package controllers;

import com.google.gson.Gson;
import entities.Comment;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Path("/comment")
public class CommentController {

    @GET
    @Path("/all")
    @RolesAllowed("Admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String getComments() throws Exception {
        String json = new Gson().toJson(Comment.listAll());

        return json;
    }

    @GET
    @PermitAll
    @Path("all/{matchId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsByMatchId(@PathParam int matchId) throws Exception {
        List<Comment> comments = Comment.list("matchId", matchId);
        comments.sort(Collections.reverseOrder());
        String json = new Gson().toJson(comments);

        return json;
    }

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentById(@QueryParam("id") long id) throws Exception {
        Comment comment = Comment.findById(id);
        String json = new Gson().toJson(comment);

        return json;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Transactional
    @Path("/send")
    public Response postComment(@QueryParam("matchId") int matchId,
                                @QueryParam("sender") String sender,
                                @QueryParam("message") String message){

        System.out.println("Sender: "+sender);
        System.out.println("message: "+message);

        Comment comment = new Comment();
        comment.matchId = matchId;
        comment.sender = sender;
        comment.message = message;
        comment.createdon = LocalDateTime.now();
        comment.persist();

        List<Comment> comments = Comment.listAll();
        comments.sort(Collections.reverseOrder());

        String json = new Gson().toJson(comments);

        return Response.ok(json).build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCommentById(@QueryParam("id") long id) throws Exception {
        Comment.deleteById(id);
        String json = new Gson().toJson(Comment.listAll());

        return Response.ok(json).build();
    }

    @PUT
    @Transactional
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMessageById(@QueryParam("id") long id,
                                      @QueryParam("message") String message) throws Exception {
        Comment comment = Comment.findById(id);
        comment.setMessage(message);
        String json = new Gson().toJson(Comment.listAll());

        return Response.ok(json).build();
    }

    @DELETE
    @Path("/all")
    @Transactional
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteAllCommentsBySender(@QueryParam("username") String sender) throws Exception {
        Comment.delete("sender", sender);
    }
}
