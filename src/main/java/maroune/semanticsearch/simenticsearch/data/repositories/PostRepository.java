package maroune.semanticsearch.simenticsearch.data.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import maroune.semanticsearch.simenticsearch.data.models.Post;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String>{
    
}
