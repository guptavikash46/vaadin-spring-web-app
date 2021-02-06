package org.vaadin.example.ui.views.list;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.backend.service.ContactService;
import org.vaadin.example.ui.ContactForm;
import org.vaadin.example.ui.MainView;

@Route(value = "", layout = MainView.class)
public class ListView extends VerticalLayout {

    private ContactService contactService;
    private Grid<Contact> dataGrid = new Grid<>(Contact.class);
    private TextField search = new TextField();
    private Button contactForm = new Button("Contact form");
    //private Div div = new Div();

    public ListView(ContactService contactService){
        this.contactService = contactService;
        //add css class name
        addClassName("list-view");
        //sets the size of the components to extend to page size
        setSizeFull();
        //handler to configure the gridView
        configureGrid();
        //manage data filters
        configureFilter();
        //attach backend data to UI
        updateList();
        //finally add the component to the parent layout
        add(contactForm, search, dataGrid);
        //navigating to contact form
        contactForm.addClickListener( e -> {
           UI.getCurrent().navigate("contact-form");
        });
    }
    public void configureGrid(){
        dataGrid.setClassName("contact-grid");
        dataGrid.setSizeFull();
        dataGrid.removeColumnByKey("company");
        dataGrid.setColumns("firstName", "lastName", "email", "status");
        //adding filter to show company name
        dataGrid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
        
        //setting auto width of columns
        dataGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    public void configureFilter() {
        search.setPlaceholder("search by name");
        search.setClearButtonVisible(true);
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.addValueChangeListener(e -> updateList());
    }
    public void updateList(){
        dataGrid.setItems(contactService.findAll(search.getValue()));
    }
    
}
