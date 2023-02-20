package it.tai.springpostresqljpa.springpostresqljpa.services;

import it.tai.springpostresqljpa.springpostresqljpa.exceptions.ResourceNotFoundException;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tag;
import it.tai.springpostresqljpa.springpostresqljpa.model.Tutorial;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TagRepository;
import it.tai.springpostresqljpa.springpostresqljpa.repository.TutorialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TagService
{
    @Autowired
    TagRepository tagRepository;

    @Autowired
    TutorialRepository tutorialRepository;

    public void tagTutorial(long tutorialId, String tagName)
    {
        Optional<Tutorial> tutorial = tutorialRepository.findById(tutorialId);
        if(tutorial.isEmpty())
            throw new ResourceNotFoundException("tutorial not found", tutorialId);
        Tag tag = this.ensureTag(tagName);
        tag.getTutorials().add(tutorial.get());
        tagRepository.saveAndFlush(tag);
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
