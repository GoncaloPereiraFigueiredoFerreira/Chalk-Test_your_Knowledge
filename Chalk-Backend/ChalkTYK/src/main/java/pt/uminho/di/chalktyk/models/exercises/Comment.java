package pt.uminho.di.chalktyk.models.exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.ItemsList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	private ItemsList items;

	public Comment(List<Item> items) {
		this.items = new ItemsList(items);
	}

	/**
	 * Adds item to the list.
	 * @param item comment item
	 */
	public void addItem(Item item){
		if(item != null){
			if(items == null)
				items = new ItemsList();
			items.add(item);
		}
	}

	/**
	 * Checks if the comment is properly formed.
	 * @return 'null' if the comment is properly formed.
	 * 			Or a string specifying the error.
	 */
	public String verifyComment(){
		if (items == null || items.isEmpty())
			return "Comment is null or empty.";
		return null;
	}

	public Comment clone(){
		if(items == null)
			return new Comment();
		else
			return new Comment(items.stream().map(Item::clone).collect(Collectors.toCollection(ItemsList::new)));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Comment comment = (Comment) o;

        return Objects.equals(items, comment.items);
    }
}