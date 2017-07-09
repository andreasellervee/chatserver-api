package chatserver.message.repository;

import chatserver.message.model.Message;
import chatserver.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(" SELECT m FROM Message m " +
            "WHERE m.from = :from " +
            "AND m.to = :to " +
            "AND m.messageStatus IN ('SENT', 'RECEIVED') " +
            "ORDER BY m.timestamp DESC")
    List<Message> findUnreadMessagesFromTo(@Param("from") User from, @Param("to") User to);

    @Query(" SELECT m FROM Message m " +
            "WHERE ((m.from = :from AND m.to = :to) " +
            "OR (m.from = :to AND m.to = :from)) " +
            "AND m.messageStatus = 'SEEN' " +
            "ORDER BY m.timestamp DESC")
    List<Message> findPreviousSeenMessagesFromTo(@Param("from") User from, @Param("to") User to, Pageable pageable);

}
