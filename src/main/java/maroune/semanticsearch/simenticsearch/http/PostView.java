package maroune.semanticsearch.simenticsearch.http;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import maroune.semanticsearch.simenticsearch.data.models.Post;
import maroune.semanticsearch.simenticsearch.data.services.PostService;
import com.vaadin.flow.data.binder.Binder;

@Route(value="post", layout = MainView.class)
public class PostView extends VerticalLayout implements  BeforeEnterObserver{

    protected final PostService service;
    protected Post post;

    protected TextField title = new TextField("Title");
    protected TextArea content = new TextArea("Content");

    /* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel", VaadinIcon.BACKWARDS.create());
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    
    Binder<Post> binder = new Binder<>(Post.class);

    @Autowired
    public PostView(PostService service) {
        
        this.service = service;
        this.post = new Post();
        title.setWidthFull();
        content.setWidthFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        add(title, content, actions);
        binder.forField(title).bind(Post::title, Post::title);
        binder.forField(content).bind(Post::content, Post::content);
        binder.setBean(post);
        setSpacing(true);
        save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> cancel());
    }

    protected void delete() {
        
        goHome();
    }

    protected void save() {
         binder.validate();
         if (binder.isValid()) {
            service.save(binder.getBean());
            goHome();
        }
        
    }
    
    protected void cancel() {
        goHome();
    }
    
    protected void goHome() {
        this.post = null;
        this.getUI().ifPresent(
                ui -> ui.navigate(""));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        
        QueryParameters queryParameters = event.getLocation().getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
       
        if (parametersMap.containsKey("id")) {
            String id = parametersMap.get("id").get(0);
            Post post = service.getPost(id);
            if(post != null)
                binder.setBean(post);
        }
    
    }
    

    
}
