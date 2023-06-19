package maroune.semanticsearch.simenticsearch.data.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${openai.token}")
    private String open_ai_key;
    
    @Autowired
	ObjectMapper objectMapper;
    
    @Autowired
    protected PostRepository repository;

    public void save(Post post) {
        Vector<Double> vector = new Vector<Double>(getEmbedding(post.title() + "" + post.content()));
        post.embedding(vector);
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
                Vector<Double> vector = new Vector<Double>(getEmbedding(item[1] + "" + item[2]));
                post.embedding(vector);
                posts.add(post);
            });
            repository.saveAll(posts);
        } catch (IOException | CsvException e) {

            e.printStackTrace();
        }
    }
    
    public List<Double> getEmbedding(String text) {

		HttpHeaders headers = new HttpHeaders();
		RestTemplate client = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(open_ai_key);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("input", text);
		data.put("model", "text-embedding-ada-002");
		String body = null;
		try {
			body = objectMapper.writeValueAsString(data);
			logger.info("JSON DATA FOR EMBEDDING"+body);
		} catch (JsonProcessingException e) {
			return null;
		}
		HttpEntity<String> request = new HttpEntity<String>(body, headers);
		try {
			ResponseEntity<String> response = client.postForEntity("https://api.openai.com/v1/embeddings", request,
					String.class);
			HttpStatusCode statusCode = response.getStatusCode();
			if (statusCode == HttpStatus.ACCEPTED || statusCode == HttpStatus.OK) {
				String result = response.getBody();
				JsonNode root;
				try {
					
					root = objectMapper.readTree(result);
					JsonNode dataRoot = root.path("data");
					final List<Double> list = new ArrayList<>();
					dataRoot.forEach(node -> {
						JsonNode embed = node.path("embedding");
						try {
							list.addAll(objectMapper.readValue(embed.traverse(),
								new TypeReference<List<Double>>() {
							}));
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					return list;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				logger.info("status " + statusCode);
			}
		} catch (BadRequest|TooManyRequests e) {
			logger.info(e.getMessage());
		}

		return null;
	}

    public void truncate() {
        repository.deleteAll();
    }
    
}
