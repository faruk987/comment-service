package controllers;

import com.google.gson.Gson;
import entities.Comment;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/comment")
public class CommentController {

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getComments() throws Exception {
        String json = new Gson().toJson(Comment.listAll());

        return json;
    }

    @GET
    @Path("all/{matchId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsByMatchId(@PathParam int matchId) throws Exception {
        List<Comment> comments = Comment.list("matchId", matchId);
        String json = new Gson().toJson(comments);

        return json;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentById(@QueryParam("id") long id) throws Exception {
        Comment comment = Comment.findById(id);
        String json = new Gson().toJson(comment);

        return json;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Transactional
    public Response postComment(@QueryParam("matchId") int matchId,
                                @QueryParam("sender") String sender,
                                @QueryParam("message") String message){
        Comment comment = new Comment();
        comment.matchId = matchId;
        comment.sender = sender;
        comment.message = message;
        comment.dateTime = LocalDateTime.now();
        comment.persist();

        String json = new Gson().toJson(Comment.listAll());

        return Response.ok(json).build();
    }

    @DELETE
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCommentById(@QueryParam("id") long id) throws Exception {
        Comment.deleteById(id);
        String json = new Gson().toJson(Comment.listAll());

        return Response.ok(json).build();
    }

    @PUT
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMessageById(@QueryParam("id") long id,
                                      @QueryParam("message") String message) throws Exception {
        Comment comment = Comment.findById(id);
        comment.setMessage(message);
        String json = new Gson().toJson(Comment.listAll());

        return Response.ok(json).build();
    }
}
