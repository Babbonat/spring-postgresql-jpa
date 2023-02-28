package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TagMapper;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.UpdateTagRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagsResponseDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TagService
{
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TutorialMapper tutorialMapper;

    public List<TagsResponseDTO> listTags()
    {
        List<TagEntity> tagEntities = new ArrayList<>();
        tagRepository.findAll().forEach(tagEntities::add);
        if(tagEntities.isEmpty())
            return null;
        List<TagsResponseDTO> response = new ArrayList<>();
        tagEntities.forEach(tag -> response.add(tagMapper.toResponse(tag)));
        return response;
    }

    public TagEntity getTag(long tagId)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        return tag.get();
    }

    public List<TutorialResponseDTO> getTutorialsByTagId(long tagId)
    {
        if(!tagRepository.existsById(tagId))
            throw new ResourceNotFoundException("tag not found", tagId);
        List<TutorialEntity> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
        if(tutorials.isEmpty())
            return null;
        List<TutorialResponseDTO> response = new ArrayList<>();
        tutorials.forEach(tutorial -> response.add(tutorialMapper.toResponse(tutorial)));
        return response;
    }

    public List<TutorialResponseDTO> getTutorialsByTagName(String tagName)
    {
        Optional<TagEntity> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagName);

        List<TutorialEntity> tutorials = tutorialRepository.findTutorialsByTagsName(tagName);
        if(tutorials.isEmpty())
            return null;
        List<TutorialResponseDTO> response = new ArrayList<>();
        tutorials.forEach(tutorial -> response.add(tutorialMapper.toResponse(tutorial)));
        return response;
    }

    public List<TagsResponseDTO> getTagsByTutorialId(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        List<TagEntity> tags = tagRepository.findTagsByTutorialsId(tutorialId);
        if(tags.isEmpty())
            return null;
        List<TagsResponseDTO> response = new ArrayList<>();
        tags.forEach(tag -> response.add(tagMapper.toResponse(tag)));
        return response;
    }

    public void tagTutorial(long tutorialId, String tagName)
    {
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        TagEntity tagEntity = this.ensureTag(tagName);
        TutorialEntity t = tutorial.get();
        t.addTag(tagEntity);
        tutorialRepository.saveAndFlush(t);
    }

    public void untagTutorial(long tutorialId, long tagId)
    {
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        TutorialEntity t = tutorial.get();
        t.removeTag(tagId);
        tutorialRepository.saveAndFlush(t);
    }

    /*public void updateTagInfo(long tagId, TagEntity newTagEntity)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        tag.get().setName(newTagEntity.getName());
    }*/

    public void updateTagInfo(long tagId, UpdateTagRequestDTO request)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        tag.get().setName(request.getName());
        tagRepository.saveAndFlush(tag.get());
    }       //DA CHIEDERE SE VA BENE

    public void deleteTag(long tagId)
    {
        if(tagId < 0)
            throw new BadParameterException("tagId");
        if(tagRepository.existsById(tagId))
            tagRepository.deleteById(tagId);
    }

    private TagEntity ensureTag(String tagName)
    {
        Optional<TagEntity> tag = tagRepository.findByName(tagName);
        if(tag.isPresent())
            return tag.get();
        TagEntity t = new TagEntity();
        t.setName(tagName);
        return tagRepository.saveAndFlush(t);
    }
}
