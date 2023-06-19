package maroune.semanticsearch.simenticsearch.http;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;

import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    public MainView() {

        H1 title = new H1("SM App");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .setWidth("200px")
                .set("margin", "0")
                .set("padding", "10px");
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setWidthFull();
        wrapper.setJustifyContentMode(JustifyContentMode.CENTER);
        wrapper.add(getTabs());
        addToNavbar(title, wrapper);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs(createMenuItems());
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);

        return tabs;
    }

    private Tab[] createMenuItems() {
        return new Tab[] { createTab("Search", VaadinIcon.SEARCH.create(), SearchView.class),
                createTab("New Post", VaadinIcon.EDIT.create(), PostView.class),
                createTab("Import posts", VaadinIcon.DOWNLOAD.create(), ImportView.class) };
    }

    private static Tab createTab(String text, Icon icon,
            Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab(icon);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }
}
