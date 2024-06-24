package br.com.hd.controllers.knowledge.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.model.knowledge.v1.Knowledge;
import br.com.hd.repositories.knowledge.v1.KnowledgeCustomRepository;

@RestController
@RequestMapping(path = "/v1/knowledge")
public class KnowledgeController {
	
	@Autowired
	private KnowledgeCustomRepository customRepository;
	
	@GetMapping
	public List<Knowledge> find() {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("title", "title");
		queryParams.put("content", "content");
		queryParams.put("softwareDescription", "software");
		queryParams.put("tagDescription", "tag");
		
		Pageable pageable = PageRequest.of(1, 2, Sort.by(Direction.DESC, "id"));
		
		
		return (List<Knowledge>) customRepository.findCustomPageable(queryParams, pageable).get("resultList");
	}

}
