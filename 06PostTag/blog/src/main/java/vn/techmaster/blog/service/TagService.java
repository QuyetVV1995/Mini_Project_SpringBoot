package vn.techmaster.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.blog.model.Tag;
import vn.techmaster.blog.repository.TagRepository;

@Service
public class TagService implements ITagService {
    
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Optional<Tag> getTagByID(Long id) {
        
        return tagRepository.findById(id) ;
    }
    
}
