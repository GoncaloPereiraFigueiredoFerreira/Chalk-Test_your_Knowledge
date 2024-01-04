package pt.uminho.di.chalktyk.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.services.ITagsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

/**
 * TagsApiController
 */
@RestController
@RequestMapping("/tags")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class TagsApiController implements TagsApi {

    private final ITagsService tagsService;

    @Autowired
    public TagsApiController(ITagsService tagsService) {
        this.tagsService = tagsService;
    }

    @Override
    public ResponseEntity<Tag> getTag(String tagId) {

        Tag tag = tagsService.getTagById(tagId);

        return ResponseEntity.ok(tag);
    }

    @Override
    public ResponseEntity<Boolean> existsTagByNameAndPath(String tagName, @Valid String tagPath) {
        Boolean ret = tagsService.existsTagByNameAndPath(tagName, tagPath);
        
        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<List<Tag>> listTagsPath(Integer levels, @Valid String tagPath) {
        List<Tag> ret;
        try {
            ret = tagsService.listTags(tagPath, levels);
        } catch (BadInputException e) {
            return new ExceptionResponseEntity<List<Tag>>().createRequest(e);
        }

        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<List<Tag>> listTagsPaths(Integer levels, @Valid List<String> tagPaths) {
        List<Tag> ret;

        try {
            ret = tagsService.listTags(tagPaths, levels);
        } catch (BadInputException e) {
            return new ExceptionResponseEntity<List<Tag>>().createRequest(e);
        }

        return ResponseEntity.ok(ret);
    }

    
}
