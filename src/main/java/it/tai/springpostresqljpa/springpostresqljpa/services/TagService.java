package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagService
{
    @Autowired
    TagRepository tagRepository;

    @Autowired
    TutorialRepository tutorialRepository;

    public List<Tag> listTags()
    {
        List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        return tags;
    }

    public Tag getTag(long tagId)
    {
        Optional<Tag> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        return tag.get();
    }

    public List<Tutorial> getTutorialsByTagId(long tagId)
    {
        if(!tagRepository.existsById(tagId))
            throw new ResourceNotFoundException("tag not found", tagId);
        return tutorialRepository.findTutorialsByTagsId(tagId);
    }

    public List<Tag> getTagsByTutorialId(long tutorialId)
    {
        if(!tutorialRepository.existsById(tutorialId))
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        return tagRepository.findTagsByTutorialsId(tutorialId);
    }

    public void tagTutorial(long tutorialId, String tagName)
    {
        Optional<Tutorial> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        Tag tag = this.ensureTag(tagName);
        Tutorial t = tutorial.get();
        t.addTag(tag);
        tutorialRepository.saveAndFlush(t);
    }

    public void untagTutorial(long tutorialId, long tagId)
    {
        Optional<Tutorial> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        Tutorial t = tutorial.get();
        t.removeTag(tagId);
        tutorialRepository.saveAndFlush(t);
    }

    public void updateTagInfo(long tagId, Tag newTag)
    {
        Optional<Tag> tag = tagRepository.findById(tagId);
        if(tag.isEmpty())
            throw new ResourceNotFoundException("tag not found", tagId);
        tag.get().setName(newTag.getName());
    }

    public void deleteTag(long tagId)
    {
        tagRepository.deleteById(tagId);
    }

    private Tag ensureTag(String tagName)
    {
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if(tag.isPresent())
            return tag.get();
        Tag t = new Tag();
        t.setName(tagName);
        return tagRepository.saveAndFlush(t);
    }
}
