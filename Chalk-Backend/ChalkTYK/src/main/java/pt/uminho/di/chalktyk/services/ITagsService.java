package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Tag;

import java.util.List;

public interface ITagsService {
    List<Tag> listTags(List<String> paths, Integer levels);
    Tag createTag(String name, String path);
    void deleteTag(String id);
}