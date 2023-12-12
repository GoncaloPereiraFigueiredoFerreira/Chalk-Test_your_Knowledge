package pt.uminho.di.chalktyk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.repositories.relational.TagSqlDAO;
import pt.uminho.di.chalktyk.services.ITagsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

@SpringBootTest
public class TestTags {

    private final ITagsService iTagsService;
    private final TagSqlDAO tagSqlDAO;

    @Autowired
    public TestTags(ITagsService iTagsService, TagSqlDAO tagSqlDAO) {
        this.iTagsService = iTagsService;
        this.tagSqlDAO = tagSqlDAO;
    }

    void _createTags() throws BadInputException{
        String tagName1 = "A", tagPath1 = "/";
        String tagName2 = "B", tagPath2 = "/B/";
        String tagName3 = "A", tagPath3 = "/A/C/";

        iTagsService.createTag(tagName1, tagPath1);
        iTagsService.createTag(tagName2, tagPath2);
        iTagsService.createTag(tagName3, tagPath3);
    }

    @Test
    @Transactional
    void createTags() throws BadInputException {
        _createTags();
        assert iTagsService.existsTagByNameAndPath("A", "/");
        assert iTagsService.existsTagByNameAndPath("C", "/A/");
        assert iTagsService.existsTagByNameAndPath("A", "/A/C/");
        assert iTagsService.existsTagByNameAndPath("B", "/");
        assert iTagsService.existsTagByNameAndPath("B", "/B/");
    }

    @Test
    @Transactional
    void testSearchByPathRegex() throws BadInputException {
        _createTags();
        System.out.println(tagSqlDAO.findByPathRegex("^/"));
    }

    @Test
    @Transactional
    void listTags() throws BadInputException {
        _createTags();
        List<TagSQL> tags = iTagsService.listTags("/", -1);
        assert existsTagByNameAndPath("A", "/", tags);
        assert existsTagByNameAndPath("C", "/A/", tags);
        assert existsTagByNameAndPath("A", "/A/C/", tags);
        assert existsTagByNameAndPath("B", "/", tags);
        assert existsTagByNameAndPath("B", "/B/", tags);

        tags = iTagsService.listTags("/A/", -1);
        assert !existsTagByNameAndPath("A", "/", tags);
        assert existsTagByNameAndPath("C", "/A/", tags);
        assert existsTagByNameAndPath("A", "/A/C/", tags);
        assert !existsTagByNameAndPath("B", "/", tags);
        assert !existsTagByNameAndPath("B", "/B/", tags);

        tags = iTagsService.listTags("/", 1);
        assert existsTagByNameAndPath("A", "/", tags);
        assert !existsTagByNameAndPath("C", "/A/", tags);
        assert !existsTagByNameAndPath("A", "/A/C/", tags);
        assert existsTagByNameAndPath("B", "/", tags);
        assert !existsTagByNameAndPath("B", "/B/", tags);

        tags = iTagsService.listTags("/", 2);
        assert existsTagByNameAndPath("A", "/", tags);
        assert existsTagByNameAndPath("C", "/A/", tags);
        assert !existsTagByNameAndPath("A", "/A/C/", tags);
        assert existsTagByNameAndPath("B", "/", tags);
        assert existsTagByNameAndPath("B", "/B/", tags);
    }

    boolean existsTagByNameAndPath(String name, String path, List<TagSQL> tags){
        for(TagSQL t : tags){
            if(t.getName().equals(name) && t.getPath().equals(path))
                return true;
        }
        return false;
    }

}
