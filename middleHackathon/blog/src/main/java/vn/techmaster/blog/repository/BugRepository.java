package vn.techmaster.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.blog.model.Bug;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long>{

}
    
