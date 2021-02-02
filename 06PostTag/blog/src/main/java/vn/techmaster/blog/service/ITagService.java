package vn.techmaster.blog.service;


import java.util.Optional;

import vn.techmaster.blog.model.Tag;

public interface ITagService {

    public Optional<Tag> getTagByID(Long id);
    
}
