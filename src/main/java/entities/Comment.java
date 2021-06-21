package entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Comment extends PanacheEntity implements Comparable<Comment>{
    public int matchId;
    public String message;
    public String sender;
    public LocalDateTime createdon;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return createdon;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.createdon = dateTime;
    }

    @Override
    public int compareTo(Comment comment) {
        return 0;
    }
}
