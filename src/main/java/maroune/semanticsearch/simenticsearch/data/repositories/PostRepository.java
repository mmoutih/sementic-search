package maroune.semanticsearch.simenticsearch.data.repositories;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import maroune.semanticsearch.simenticsearch.data.models.Post;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String>{
    
    @Query("{" +
			"    \"script_score\": {" +
			"     	\"query\": {\"match_all\": {}}," +
			"		\"script\": {"+
			"			\"source\": \"cosineSimilarity(params.queryVector, 'embedding') +0.1\","+
			"			\"params\": {\"queryVector\": ?0}"+
			"		}"+
			"	}"+
			"}")
	List<Post> findBySimilar( String content);
}
