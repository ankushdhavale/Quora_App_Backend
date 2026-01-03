package com.ankush.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ankush.dtos.TagDto;
import com.ankush.models.Tag;
import com.ankush.repositories.TagRepository;

@Service
public class TagService {
	
	private TagRepository tagRepository;
	
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	public List<Tag> getAllTag(){
		return tagRepository.findAll();
	}
	
	public Optional<Tag> getTagById(Long id) {
		return tagRepository.findById(id);
	}
	public Tag createTag(TagDto tagDto) {
		Tag tag = new Tag();
		tag.setName(tagDto.getName());
		return tagRepository.save(tag);
	}
	
	public void deletTag(Long id) {
		tagRepository.deleteById(id);
	}
}
