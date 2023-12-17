package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

public interface ITagsService {
    /**
     * Creates a tag. If the tags on the path do not exist, they are also created.
     * @param name Tag's name. Cannot contain the "/" character.
     * @param path Tag's path. Must follow the following regex expression "^(\/[^\/\n]*)*\/$"
     * @return the tag
     * @throws BadInputException when the name or the path is not valid.
     */
    TagSQL createTag(String name, String path) throws BadInputException;

    /**
     * Deletes a tag.
     * @param id identifier of the tag
     */
    void deleteTag(String id);

    /**
     * Retrieves a tag using the identifier.
     * @param id identifier of the tag
     * @return the tag that matches the given identifier, or 'null' if no tag exists with the given identifier
     */
    TagSQL getTagById(String id);

    /**
     * @param name of the tag
     * @param path of the tag
     * @return 'true' if a tag exists with the given name and path
     */
    boolean existsTagByNameAndPath(String name, String path);

    /**
     * List tags starting at the given path.
     * @param path that marks the starts of the search
     * @param levels depth of the search. -1 for all levels.
     * @return list of tags which depth is lower or equal to the given depth,
     * and with the search starting at the given path
     * @throws BadInputException if 'levels' is not -1 or a positive number, or if there are any invalid paths.
     */
    List<TagSQL> listTags(String path, Integer levels) throws BadInputException;

    /**
     * List tags starting at the given paths.
     * @param paths list of paths
     * @param levels depth of the search. -1 for all levels.
     * @return list of tags which depth is lower or equal to the given depth,
     * and with the search starting at the given paths
     * @throws BadInputException if 'levels' is not -1 or a positive number, or if there are any invalid paths.
     */
    List<TagSQL> listTags(List<String> paths, Integer levels) throws BadInputException;
}