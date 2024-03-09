package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.Note;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public interface NoteRepository extends CassandraRepository<Note, Long> {
}
