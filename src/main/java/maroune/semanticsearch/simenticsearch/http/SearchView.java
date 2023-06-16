package maroune.semanticsearch.simenticsearch.http;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import maroune.semanticsearch.simenticsearch.data.models.Post;

@Route(value = "", layout = MainView.class)
public class SearchView extends VerticalLayout {

    public SearchView() {
        add(getFormSearch(), addGrid());
    }

    private Component addGrid() {
        Grid<Post> grid = new Grid<>(Post.class, isAttached());
        grid.addColumn(Post::id).setHeader("Id");
        grid.addColumn(Post::title).setHeader("Title");
        grid.addComponentColumn(item -> {
            HorizontalLayout wrapper = new HorizontalLayout();
            wrapper.add( new Button(VaadinIcon.EDIT.create(), click -> {
                (new Notification()).show("edit");
            }));
             wrapper.add( new Button(VaadinIcon.TRASH.create(), click -> {
                (new Notification()).show("delete");
            }));
            return wrapper;
        });
        List<Post> posts = new ArrayList<>();
        posts.add(
            new Post("one", "test", "test", null)
        );
        posts.add(
            new Post("tow", "test tow", "test", null)
        );
        grid.setItems(posts);
        return grid;
    }

    private Component getFormSearch() {
        FormLayout formSearch = new FormLayout();
        TextField searchField = new TextField();
        Button searchButton = new Button(VaadinIcon.SEARCH_MINUS.create(), e -> {
            (new Notification()).show("click");
        });
        searchField.setPlaceholder("Your Search");
        formSearch.add(
                searchField,
                searchButton);
        return formSearch;
    }

}
