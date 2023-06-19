package maroune.semanticsearch.simenticsearch.http;

import java.io.InputStream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;

import maroune.semanticsearch.simenticsearch.data.services.PostService;

@Route(value="import", layout = MainView.class)
public class ImportView extends VerticalLayout {
    

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    Button reset = new Button("Truncate table posts", VaadinIcon.WARNING.create());
    protected final PostService service;

    ImportView(PostService service) {
        this.service = service;
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            service.parseCsvFile(inputStream);
            goHome();
        });
        upload.setWidthFull();
        upload.setHeight("200px");
        reset.getStyle()
        .set("color", "aliceblue")
                .set("background-color", "red");
        reset.addClickListener(e->{
            service.truncate();
            goHome();
        });
        this.setAlignItems(Alignment.CENTER);
        add(upload, reset);
    }

    protected void goHome() {
        
        this.getUI().ifPresent(
                ui -> ui.navigate(""));
    }
}
