package maroune.semanticsearch.simenticsearch.data.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
