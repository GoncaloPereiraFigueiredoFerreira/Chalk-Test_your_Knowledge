package pt.uminho.di.chalktyk.apis.utility;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class CustomPage<T>{
    private final List<T> items;
    private final int totalPages; // total number of pages
    public CustomPage(Page<T> page){
        if(page == null){
            items = new ArrayList<>();
            totalPages = 0;
        }else{
            items = page.getContent();
            totalPages = page.getTotalPages();
        }
    }
    public CustomPage(List<T> items, int totalPages){
        this.items = items != null ? items : new ArrayList<>();
        this.totalPages = Integer.max(0, totalPages);
    }
}