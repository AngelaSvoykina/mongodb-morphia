package application.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

// Сущность проекта в Morphia
@Entity("project")
public class Project {
    private @Id ObjectId id;
    private String title;
    private Date publicationDate;

    public Project(String title, Date publicationDate) {
        this.title = title;
        this.publicationDate = publicationDate;
    }
}
