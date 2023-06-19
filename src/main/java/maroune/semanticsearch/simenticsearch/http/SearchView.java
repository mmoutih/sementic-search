package maroune.semanticsearch.simenticsearch.http;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import maroune.semanticsearch.simenticsearch.data.models.Post;
import maroune.semanticsearch.simenticsearch.data.services.PostService;

@Route(value = "", layout = MainView.class)
public class SearchView extends VerticalLayout{

    protected final PostService service;
    protected int rowIndex = 0;

    @Autowired
    public SearchView(PostService service) {
        this.service = service;
        this.setHeightFull();
        add(getFormSearch(), addGrid());
    }

    private Component addGrid() {
        Grid<Post> grid = new Grid<>(Post.class, isAttached());
        grid.addComponentColumn(item -> {
            return new Span(getRowIndex());
        }).setHeader("#");
        grid.addColumn(Post::id).setHeader("Id");
        grid.addColumn(Post::title).setHeader("Title");
        grid.addComponentColumn(item -> {
            HorizontalLayout wrapper = new HorizontalLayout();
            wrapper.add( new Button(VaadinIcon.EDIT.create(), click -> {
                goPost(item);
            }));
            wrapper.add(new Button(VaadinIcon.TRASH.create(), click -> {
                service.delete(item);
                grid.setItems(service.getAll());
                (new Notification()).show("post deleted");
            }));
            return wrapper;
        });
        grid.setHeightFull();
        grid.setItems(service.getAll());
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setWidthFull();
        wrapper.setHeightFull();
        wrapper.add(grid);
        return wrapper;
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

    protected void goPost(Post post) {

        this.getUI().ifPresent(
                ui -> ui.navigate("post?id=" + post.id()));
    }
    
    protected String getRowIndex(){
        return String.valueOf(++this.rowIndex); // increments the rowIndex for each row
    }

}
