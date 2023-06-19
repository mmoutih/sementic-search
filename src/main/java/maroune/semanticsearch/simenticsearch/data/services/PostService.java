package maroune.semanticsearch.simenticsearch.data.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import maroune.semanticsearch.simenticsearch.data.models.Post;
import maroune.semanticsearch.simenticsearch.data.repositories.PostRepository;

@Service
public class PostService {
    
    Logger logger = LoggerFactory.getLogger(PostService.class);
    
    @Autowired
    protected PostRepository repository;

    public void save(Post post) {
        logger.info(post.toString());
        repository.save(post);
    }

    public List<Post> getAll() {
        return toList(repository.findAll());
    }

    private List<Post> toList(Iterable<Post> all) {
        Iterator<Post> iterator = all.iterator();
        List<Post> posts= new ArrayList<>();
        iterator.forEachRemaining(posts::add);
        return posts;
    }

    public void delete(Post item) {
        repository.delete(item);
    }

    public Post getPost(String id) {
        Optional<Post> post = repository.findById(id);
        if (post.isPresent())
            return post.get();
        return null;
    }


    public void parseCsvFile(InputStream inputStream) 
    {
       
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')

                    //.withStrictQuotes(true)
                    .build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
            .withSkipLines(1)
            .withCSVParser(parser)
                    .build();
            try {
                List<String[]> all = reader.readAll();
                List<Post> posts = new ArrayList<>();
                all.forEach(item -> {
                    Post post = new Post(item[1], item[2]);
                    posts.add(post);
                });
                repository.saveAll(posts);
            } catch (IOException | CsvException e) {
               
                e.printStackTrace();
            }
    }

    public void truncate() {
        repository.deleteAll();
    }
    
}
