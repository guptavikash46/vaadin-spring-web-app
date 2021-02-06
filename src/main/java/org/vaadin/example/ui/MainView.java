package org.vaadin.example.ui;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.backend.service.ContactService;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */

@Route("")
public class MainView extends VerticalLayout {
    private ContactService contactService;
    private Grid<Contact> dataGrid = new Grid<>(Contact.class);
    private TextField search = new TextField();

    public MainView(ContactService contactService){
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
        add(search, dataGrid);
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
