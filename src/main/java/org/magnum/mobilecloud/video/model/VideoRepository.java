package org.magnum.mobilecloud.video.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
  
  Iterable<Video> findByName(String name);
  
  Iterable<Video> findByDurationLessThan(long duration);
  
}
