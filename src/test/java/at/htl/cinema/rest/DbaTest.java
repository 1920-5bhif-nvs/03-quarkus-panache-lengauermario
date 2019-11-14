package at.htl.cinema.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.db.api.Assertions.assertThat;

@QuarkusTest
public class DbaTest {
    Source dataSource = new Source("jdbc:postgresql://192.168.99.100/postgres", "postgres", "passme");
    @Test
    public void IsTableCinemaCreated() {
        Table table = new Table(dataSource, "cinema");
        assertThat(table).hasNumberOfColumns(4);
    }

    @Test
    public void IsTableHallCreated() {
        Table table = new Table(dataSource, "hall");
        assertThat(table).hasNumberOfColumns(4);
    }
}
