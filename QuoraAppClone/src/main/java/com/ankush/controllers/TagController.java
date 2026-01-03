package com.ankush.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.ankush.dtos.TagDto;
import com.ankush.models.Tag;
import com.ankush.services.TagService;

@RestController
@RequestMapping("/app/v1/tags")
public class TagController {

	private TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}
	
	@GetMapping
	public ResponseEntity<List<Tag>> getAllTags(){
		List<Tag> allTag = tagService.getAllTag();
		return ResponseEntity.ok(allTag);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tag> getTagById(@PathVariable Long id){
		Tag tag = tagService.getTagById(id).orElseThrow(()->new RuntimeException("Tag Not Founde"));
		return ResponseEntity.ok(tag);
	}
	
	@PostMapping
	public ResponseEntity<Tag> createTag(@RequestBody TagDto tagDto){
		Tag tag = tagService.createTag(tagDto);
		return ResponseEntity.ok(tag);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable Long id){
		tagService.deletTag(id);
		return ResponseEntity.noContent().build();
	}
}
