package vn.techmaster.blog.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import vn.techmaster.blog.controller.request.BugRequest;
import vn.techmaster.blog.model.Bug;

@Mapper
public interface BugMapper {

  BugMapper INSTANCE = Mappers.getMapper(BugMapper.class);
  
  Bug bugRequestToBug(BugRequest bugRequest);


  @Mapping(target="user_id", source="bug.user.id")
  BugRequest bugtoBugRequest(Bug bug);

    
}
