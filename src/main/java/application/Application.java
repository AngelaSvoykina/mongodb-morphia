package application;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.MongoException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import application.entities.Student;
import application.entities.Project;

import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Application {
    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "morphia";
    private static final String DB_USER = "morphia";
    private static final String DB_PASSWORD = "morphia";

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        try {
            MongoClient mongo = new MongoClient(new ServerAddress(DB_HOST, DB_PORT));
            Datastore datastore = new Morphia().mapPackageFromClass(Student.class).
                    createDatastore(mongo, DB_NAME, DB_USER, DB_PASSWORD.toCharArray());
            datastore.ensureIndexes();

            LOG.info("Connection to database '" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "' initialized");

            Project project = new Project("MongoDB Morphia", new Date());
            datastore.save(project);

            Student angelika = new Student("Angelika");
            angelika.setProjects(Stream.of(project).collect(Collectors.toSet()));
            datastore.save(angelika);

            System.out.println("Done");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}

