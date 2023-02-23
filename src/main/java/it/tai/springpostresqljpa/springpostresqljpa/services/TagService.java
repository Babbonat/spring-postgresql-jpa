package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TagEntity;
import it.tai.springpostresqljpa.springpostresqljpa.domain.TutorialEntity;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
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

    public List<TagEntity> listTags()
    {
        List<TagEntity> tagEntities = new ArrayList<>();
        tagRepository.findAll().forEach(tagEntities::add);
        return tagEntities;
    }

    public TagEntity getTag(long tagId)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        return tag.get();
    }

    public List<TutorialEntity> getTutorialsByTagId(long tagId)
    {
        if(!tagRepository.existsById(tagId))
            throw new ResourceNotFoundException("tag not found", tagId);
        return tutorialRepository.findTutorialsByTagsId(tagId);
    }

    public List<TutorialEntity> getTutorialsByTagName(String tagName)
    {
        Optional<TagEntity> tag = tagRepository.findByName(tagName);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagName);
        return tutorialRepository.findTutorialsByTagsId(tag.get().getId());
    }

    public List<TagEntity> getTagsByTutorialId(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        return tagRepository.findTagsByTutorialsId(tutorialId);
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

    public void updateTagInfo(long tagId, TagEntity newTagEntity)
    {
        Optional<TagEntity> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        tag.get().setName(newTagEntity.getName());
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
