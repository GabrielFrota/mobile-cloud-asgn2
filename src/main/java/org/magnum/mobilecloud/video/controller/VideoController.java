package org.magnum.mobilecloud.video.controller;

import java.security.Principal;
import java.util.Collection;

import org.magnum.mobilecloud.video.model.Video;
import org.magnum.mobilecloud.video.model.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/video")
public class VideoController {
  
  @Autowired private VideoRepository vidRep;
  
  @GetMapping
  public Collection<Video> getAllVideos() {   
    return (Collection<Video>) vidRep.findAll();
  }
  
  @PostMapping
  public Video addVideo(@RequestBody Video v) {
    return vidRep.save(v);
  }
  
  @GetMapping("/{id}")
  public Video getVideo(@PathVariable long id) {
    var vo = vidRep.findById(id);
    if (vo.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return vo.get();
  }
  
  @PostMapping("/{id}/like")
  public void likeVideo(@PathVariable long id, Principal princ) {
    var vo = vidRep.findById(id);
    if (vo.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    var v = vo.get();
    var name = princ.getName();
    if (v.getLikedBy().contains(name)) 
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    v.setLikes(v.getLikes() + 1);
    v.getLikedBy().add(name);
    vidRep.save(v);
  }
  
  @PostMapping("/{id}/unlike")
  public void unlikeVideo(@PathVariable long id, Principal princ) {
    var vo = vidRep.findById(id);
    if (vo.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    var v = vo.get();
    var name = princ.getName();
    if (!v.getLikedBy().contains(name)) 
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    v.setLikes(v.getLikes() - 1);
    v.getLikedBy().remove(name);
    vidRep.save(v);
  }
  
  @GetMapping("/search/findByName")
  public Collection<Video> findTitle(@RequestParam String title) {
    return (Collection<Video>) vidRep.findByName(title);
  }
  
  @GetMapping("/search/findByDurationLessThan")
  public Collection<Video> findByDurationLessThan(@RequestParam long duration) {
    return (Collection<Video>) vidRep.findByDurationLessThan(duration);
  }

}
