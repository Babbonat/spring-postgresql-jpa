package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.BadParameterException;
import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TagMapper;
import it.tai.springpostresqljpa.springpostresqljpa.mapper.TutorialMapper;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagRequestDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tutorialsDTO.TutorialResponseDTO;
import it.tai.springpostresqljpa.springpostresqljpa.services.dto.tagsDTO.TagResponseDTO;
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

    public List<TagResponseDTO> listTags()
    {
        List<TagEntity> tagEntities = new ArrayList<>();
        tagRepository.findAll().forEach(tagEntities::add);
        if(tagEntities.isEmpty())
            throw new ResourceNotFoundException("tags not found");
        List<TagResponseDTO> response = new ArrayList<>();
        tagEntities.forEach(tag -> response.add(tagMapper.toResponse(tag)));
        return response;
    }

    public TagResponseDTO getTag(long tagId)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        TagResponseDTO response = tagMapper.toResponse(tag.get());
        return response;
    }

    public List<TutorialResponseDTO> getTutorialsByTagName(String tagName)
    {
        Optional<TagEntity> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagName);
        List<TutorialEntity> tutorials = tutorialRepository.findTutorialsByTagsName(tagName);
        if(tutorials.isEmpty())
            throw new ResourceNotFoundException("tutorials not found");
        List<TutorialResponseDTO> response = new ArrayList<>();
        tutorials.forEach(tutorial -> response.add(tutorialMapper.toResponse(tutorial)));
        return response;
    }

    public List<TagResponseDTO> getTagsByTutorialId(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        List<TagEntity> tags = tagRepository.findTagsByTutorialsId(tutorialId);
        if(tags.isEmpty())
            throw new ResourceNotFoundException("tags not found");
        List<TagResponseDTO> response = new ArrayList<>();
        tags.forEach(tag -> response.add(tagMapper.toResponse(tag)));
        return response;
    }

    public TutorialResponseDTO tagTutorial(long tutorialId, TagRequestDTO request)
    {
        Optional<TutorialEntity> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        TagEntity tagEntity = this.ensureTag(request.getName());
        TutorialEntity t = tutorial.get();
        if(!t.getTags().contains(tagEntity))
            t.addTag(tagEntity);
        tutorialRepository.saveAndFlush(t);
        return tutorialMapper.toResponse(t);
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

    public TagResponseDTO updateTagInfo(long tagId, TagRequestDTO request)
    {
        if(request == null)
            throw new BadParameterException("request");
        if(request.getName() == null)
            throw new BadParameterException("request.name");
        Optional<TagEntity> tagToUpdate = tagRepository.findById(tagId);
        if(tagToUpdate.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        TagEntity tag = tagMapper.toEntity(request);
        TagEntity response = tagToUpdate.get();
        response.setName(tag.getName());
        tagRepository.saveAndFlush(response);
        return tagMapper.toResponse(response);
    }

    public void deleteTag(long tagId)
    {
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
