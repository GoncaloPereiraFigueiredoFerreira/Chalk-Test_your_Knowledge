package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.repositories.TagDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TagsService implements ITagsService {
    private final TagDAO tagDAO;

    @Autowired
    public TagsService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    /**
     * @return 'true' if the string has the format of a path that follows the regular expression: "^(\/[^\/\n]*)*\/$"
     */
    private boolean isValidPath(String s){
        return Pattern.compile("^(/[^/\\n]*)*/$").matcher(s).matches();
    }

    /**
     * Creates a tag. If the tags on the path do not exist, they are also created.
     * Example 1:
     *   path = "/"
     *   name = "Math"
     * Example 2:
     *   path = "/Math/"
     *   name = "Geometry"
     * @param name Tag's name
     * @param path Tag's path. Must follow the following regular expression "^(\/[^\/\n]*)*\/$"
     * @return the tag
     */
    @Override
    public Tag createTag(String name, String path) throws BadInputException {
        if (name.contains("/"))
            throw new BadInputException("The name of a tag cannot contain the '/' character.");

        if(!isValidPath(path))
            throw new BadInputException("The path of the tag does not follow the following regular expression: \"^(\\/[^\\/\\n]*)*\\/$\"");

        Tag t = tagDAO.findByNameAndPath(name, path);
        if(t != null)
            return t;

        // Creates the tags on the path that do not exist.
        createPath(path);

        // Creates the tag
        return tagDAO.save(new Tag(null, name, path));
    }

    /**
     * Creates the tags on the path that do not exist.
     * @param path string that contain tags that form a path
     */
    private void createPath(String path){
        String[] tagsOnPath = path.split("/");

        // Has an empty string at the start because all paths start with a '/', so it needs to be removed.
        tagsOnPath = tagsOnPath.length > 1 ? Arrays.copyOfRange(tagsOnPath, 1, tagsOnPath.length) : tagsOnPath;

        int i = tagsOnPath.length - 1;

        // finds the existent part of the path
        for(;i >= 0; i--){
            String auxName = tagsOnPath[i];
            String auxPath = createPathString(Arrays.copyOfRange(tagsOnPath, 0, i));
            if (tagDAO.existsByNameAndPath(auxName, auxPath))
                break;
        }

        // adds 1 to the index, because the tag at which the index pointed already exists
        i++;

        // creates the rest of the path
        for(; i < tagsOnPath.length; i++){
            String auxName = tagsOnPath[i];
            String auxPath = createPathString(Arrays.copyOfRange(tagsOnPath, 0, i));
            tagDAO.save(new Tag(null, auxName, auxPath));
        }

        System.out.flush();

    }


    /**
     * From a list of tags, creates a path.
     * Example:
     *  in -> tags = ["Math", "Geometry"] ;
     *  out -> "/Math/Geometry/"
     * @param tags ordered list of tags to create the path
     * @return path containing the given tags
     */
    private String createPathString(String[] tags){
        StringBuilder stringBuilder = new StringBuilder();
        for(String t : tags)
            stringBuilder.append("/").append(t);
        stringBuilder.append("/");
        return stringBuilder.toString();
    }

    /**
     * Deletes a tag.
     *
     * @param id identifier of the tag
     */
    @Override
    public void deleteTag(String id) {
        tagDAO.deleteById(id);
    }

    /**
     * Retrieves a tag using the identifier.
     *
     * @param id identifier of the tag
     * @return the tag that matches the given identifier, or 'null' if no tag exists with the given identifier
     */
    @Override
    public Tag getTagById(String id) {
        return tagDAO.findById(id).orElse(null);
    }

    /**
     * @param name of the tag
     * @param path of the tag
     * @return 'true' if a tag exists with the given name and path
     */
    @Override
    public boolean existsTagByNameAndPath(String name, String path) {
        return tagDAO.existsByNameAndPath(name, path);
    }

    /**
     * List tags starting at the given path.
     * @param path that marks the starts of the search
     * @param levels depth of the search. -1 for all levels.
     * @return list of tags which depth is lower or equal to the given depth,
     * and with the search starting at the given path
     * @throws BadInputException if 'levels' is not -1 or a positive number, or if there are any invalid paths.
     */
    public List<Tag> listTags(String path, Integer levels) throws BadInputException{
        StringBuilder pathRegexBuilder = new StringBuilder("^" + path);
        String pathRegex;

        if(!isValidPath(path))
            throw new BadInputException("Not a valid path.");

        if (levels == -1){
            pathRegex = pathRegexBuilder.toString();
            return tagDAO.findByPathRegex(pathRegex);
        }else if (levels >= 1){
            pathRegex = pathRegexBuilder.append("$").toString();
            List<Tag> tagList = tagDAO.findByPathRegex(pathRegex);
            for (int i = 1; i < levels; i++){
                pathRegexBuilder.deleteCharAt(pathRegexBuilder.length() - 1); // delete '$' char
                pathRegexBuilder.append("[^/\n]*/$");
                pathRegex = pathRegexBuilder.toString();
                tagList.addAll(tagDAO.findByPathRegex(pathRegex));
            }
            return tagList;
        }else {
            throw new BadInputException("Argument 'levels' can either be -1 or a positive number.");
        }
    }

    /**
     * List tags starting at the given paths.
     * @param paths list of paths
     * @param levels depth of the search. -1 for all levels.
     * @return list of tags which depth is lower or equal to the given depth,
     * and with the search starting at the given paths
     * @throws BadInputException if 'levels' is not -1 or a positive number, or if there are any invalid paths.
     */
    public List<Tag> listTags(List<String> paths, Integer levels) throws BadInputException{
        List<Tag> tagList = new ArrayList<>();
        for(String path : paths){
            tagList.addAll(listTags(path, levels));
        }
        return tagList;
    }
}
